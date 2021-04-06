package com.cav.currencyexchange.service.matching;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.models.Status;
import com.cav.currencyexchange.service.ServiceBase;


public class MatchingServiceImpl extends ServiceBase implements MatchingService {
	
	String currencyKeyPair;
	static final Logger log = LoggerFactory.getLogger(MatchingServiceImpl.class);
	String currencyA = null;
	String currencyB = null;


	public MatchingServiceImpl(String currencyKeyPair) {
		super();
		this.currencyKeyPair = currencyKeyPair;
		String[] fields = currencyKeyPair.split("/");
		currencyA = fields[0];
		currencyB = fields[1];
	}

	@Override
	public Object call() throws Exception {
		fullMatched(currencyKeyPair);
		return null;
	}
	
	/**
	 * When you buy a currency pair from a forex broker, you buy the base currency 
	 * and sell the quote currency. Conversely, when you sell the currency pair, 
	 * you sell the base currency and receive the quote currency. 
	 * Currency pairs are quoted based on their bid (buy) and ask prices (sell)
	 * @param currencyKeyPair
	 */
	private void fullMatched(String currencyKeyPair) {
		LocalDateTime checkDateTime = LocalDateTime.now();
		
		CopyOnWriteArrayList<CurrencyOrder> buys = OrdersCache.buyOrders.get(currencyKeyPair);
		CopyOnWriteArrayList<CurrencyOrder> sells = OrdersCache.sellOrders.get(currencyKeyPair);
		if(buys != null && sells != null){
			for(CurrencyOrder buy : buys){
				if(hasFunds(buy.getPartnerId()+currencyA, buy.getAmount()) && buy.getExpirationDate().isAfter(checkDateTime) && buy.getStatus().equals(Status.UNMATCHED)){
					buy.setStatus(Status.MATCHED);
					for(CurrencyOrder sell : sells){
						BigDecimal sellAmount = getAmount(buy.getAmount(), buy.getExchangeRate());
						if(hasFunds(sell.getPartnerId() +currencyB, sellAmount)  && sell.getExpirationDate().isAfter(checkDateTime) && sell.getStatus().equals(Status.UNMATCHED)){
							if(matchFull(buy, sell, currencyA, currencyB, sellAmount)) {
								break;
							}
						} else {
							buy.setStatus(Status.UNMATCHED);
						}
					}
					
				}
			}
		}
			
	}

}
