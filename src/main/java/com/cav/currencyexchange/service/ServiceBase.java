package com.cav.currencyexchange.service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.cache.AccountCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.models.Status;
import com.cav.currencyexchangebroker.generated.Orders;



public abstract class ServiceBase {

	protected static final String SELL ="Sell";
	protected static final String BUY ="Buy";
	
	private static final Logger log = LoggerFactory.getLogger(ServiceBase.class);
	
	
	protected Orders streamOrders(InputStream stream){
		 JAXBContext jaxbContext;
		 Orders orders = null;
		try {
			jaxbContext = JAXBContext.newInstance(Orders.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			orders = (Orders) jaxbUnmarshaller.unmarshal(stream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stream != null){
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return orders;
			
	}
	
	protected String getOrdersXML(InputStream stream){
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String xml = null;
		
		try {
			sb = new StringBuilder();
			br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while(null != (line = br.readLine())){
				sb.append(line);
				sb.append("\n");
			}
			xml = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(stream != null){
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return xml;
	}
	
	protected boolean hasFunds(String key, BigDecimal amount) { 
		BigDecimal checkAmount = AccountCache.accounts.get(key);
		if (checkAmount != null) {
			if (checkAmount.compareTo(amount) >= 0) {
				return true;
			}
		}
		log.info("************************ RUN OUT OF FUNDS ***********************************");
		return false;
	}
	
	/**
	 * I looked into using Money I found that it took longer than just using BigDecimal and 
	 * the precision was the same.
	 * @param amount
	 * @param rate
	 * @return
	 */
	protected BigDecimal getAmount(BigDecimal amount, BigDecimal rate) {
		return amount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	
	/**
	 * currencyKeyPair baseCurrency/quoteCurrency
	 * @param buy
	 * @param sell
	 * @param baseCurrency
	 * @param quoteCurrency
	 * @param buyAmount
	 * @param sellAmount
	 */
	protected boolean matchFull(CurrencyOrder buy, CurrencyOrder sell, String baseCurrency,  String quoteCurrency, BigDecimal sellAmount) {
		sell.setStatus(Status.MATCHED);
		BigDecimal buyAmount = buy.getAmount();
		String partnerABaseCurrency = buy.getPartnerId() + baseCurrency;
		String partnerAQuoteCurrency = buy.getPartnerId() + quoteCurrency;
		String partnerBBaseCurrency = sell.getPartnerId() + baseCurrency;
		String partnerBQuoteCurrency = sell.getPartnerId() + quoteCurrency;
		
			// Needs to be ACID for buy and sell.
			synchronized(AccountCache.accounts) {
				//Need to check again, there is enough currency in both to carry out the transaction
				//Another thread may have completed.
				if(hasFunds(partnerABaseCurrency, buyAmount) && hasFunds(partnerBQuoteCurrency, sellAmount)) {
					//Partner A Buy BaseCurrency for QuoteCurrency
					AccountCache.accounts.merge(partnerAQuoteCurrency, sellAmount, BigDecimal::add);
					AccountCache.accounts.merge(partnerABaseCurrency, buyAmount, BigDecimal::subtract);
					// Partner B Sell QuoteCurrency for BaseCurrency
					AccountCache.accounts.merge(partnerBQuoteCurrency, sellAmount, BigDecimal::subtract);
					AccountCache.accounts.merge(partnerBBaseCurrency, buyAmount, BigDecimal::add);

					buy.setStatus(Status.REMOVE);
					sell.setStatus(Status.REMOVE);
					return true;
						
				} 
				buy.setStatus(Status.UNMATCHED);
				sell.setStatus(Status.UNMATCHED);
				
		}
		return false;
	}
	
	
	
	protected void writeToFile(String path, List <CurrencyOrder> orders) {
		FileWriter myWriter = null;
		 try {
			myWriter = new FileWriter(path.toString());
			for(CurrencyOrder order : orders){
				myWriter.write(order.toString());
				myWriter.write("\r");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(myWriter != null){
				try {
					myWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
