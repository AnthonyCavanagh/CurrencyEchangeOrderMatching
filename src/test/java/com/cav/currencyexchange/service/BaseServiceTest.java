package com.cav.currencyexchange.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.cav.currencyexchange.cache.AccountCache;
import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.service.matching.MatchingService;
import com.cav.currencyexchange.service.matching.MatchingServiceImpl;
import com.cav.currencyexchange.service.matching.MatchingServiceMultiProcessingImpl;
import com.cav.currencyexchange.service.orders.OrderService;
import com.cav.currencyexchange.service.orders.OrderServiceImpl;
import com.cav.currencyexchange.service.remove.RemoveOrderService;
import com.cav.currencyexchange.service.remove.RemoveOrderServiceImpl;

public class BaseServiceTest {

	protected static final String PARTNER_A_GBP = "PartnerAGBP";
	protected static final String PARTNER_A_USD = "PartnerAUSD";
	
	protected static final String PARTNER_B_GBP = "PartnerBGBP";
	protected static final String PARTNER_B_USD = "PartnerBUSD";
	
	protected static final String PARTNER_C_GBP = "PartnerCGBP";
	protected static final String PARTNER_C_USD = "PartnerCUSD";
	
	protected void loadOrders(String [] xmlOrders){
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for(String path: xmlOrders){
			 InputStream stream = getStream(path);
			 OrderService service = new OrderServiceImpl(stream);
			 executor.submit(service);
		}
		executor.shutdown();
			try {
				executor.awaitTermination(2, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	protected void matchOrders(){
		ExecutorService executor = Executors.newFixedThreadPool(15);
		MatchingService service = null;
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> buyEntry : OrdersCache.buyOrders.entrySet()){
			service = new MatchingServiceImpl(buyEntry.getKey());
			executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void matchOrdersMultiProccessing(){
		ExecutorService executor = Executors.newFixedThreadPool(15);
		MatchingService service = null;
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> buyEntry : OrdersCache.buyOrders.entrySet()){
			service = new MatchingServiceMultiProcessingImpl(buyEntry.getKey(), null);
			executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void removeOrders(){
		ExecutorService executor = Executors.newFixedThreadPool(15);
		RemoveOrderService service = null;
		System.out.println("Start Test ");
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			service = new RemoveOrderServiceImpl(sellEntry.getKey(), "Sell");
			executor.submit(service);
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> buyEntry : OrdersCache.buyOrders.entrySet()){
			service = new RemoveOrderServiceImpl(buyEntry.getKey(), "Buy");
			executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("End Test ");
	}
	
	protected void loadRemoveOrders(String [] xmlOrders){
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for(String path: xmlOrders){
			 InputStream stream = getStream(path);
			 OrderService service = new OrderServiceImpl(stream);
			 executor.submit(service);
		}
		RemoveOrderServiceImpl service;
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			service = new RemoveOrderServiceImpl(sellEntry.getKey(), "Sell");
			executor.submit(service);
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> buyEntry : OrdersCache.buyOrders.entrySet()){
			service = new RemoveOrderServiceImpl(buyEntry.getKey(), "Buy");
			executor.submit(service);
		}
		executor.shutdown();
			try {
				executor.awaitTermination(2, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	private InputStream getStream(String path){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
		return in;
	}
	
	 protected void addAccountFullMatch2PartnersBasic() {
		AccountCache.accounts.put(PARTNER_A_GBP, new BigDecimal(5000));
		AccountCache.accounts.put(PARTNER_A_USD, new BigDecimal(5000));
		AccountCache.accounts.put(PARTNER_B_GBP, new BigDecimal(5000));
		AccountCache.accounts.put(PARTNER_B_USD, new BigDecimal(5000));
	  }
	 
	 protected void addAccountFullMatch2Partners() {
			AccountCache.accounts.put(PARTNER_A_GBP, new BigDecimal(1000000));
			AccountCache.accounts.put(PARTNER_A_USD, new BigDecimal(1000000));
			AccountCache.accounts.put(PARTNER_B_GBP, new BigDecimal(1000000));
			AccountCache.accounts.put(PARTNER_B_USD, new BigDecimal(1000000));
		  }
	 
	 protected void addAccountFullMatch3PartnersBasic() {
			AccountCache.accounts.put(PARTNER_A_GBP, new BigDecimal(5000));
			AccountCache.accounts.put(PARTNER_A_USD, new BigDecimal(5000));
			AccountCache.accounts.put(PARTNER_B_GBP, new BigDecimal(5000));
			AccountCache.accounts.put(PARTNER_B_USD, new BigDecimal(5000));
			AccountCache.accounts.put(PARTNER_C_GBP, new BigDecimal(5000));
			AccountCache.accounts.put(PARTNER_C_USD, new BigDecimal(5000));
		  }
}
