package com.cav.currencyexchange.service.matching;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.cache.AccountCache;
import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.models.Status;
import com.cav.currencyexchange.service.ServiceBase;

/**
 * Will match orders, only matches full orders at the moment
 * @author Tony
 *
 */
public class MatchingServiceMultiProcessingImpl extends ServiceBase implements MatchingService {
	
	
	static final Logger log = LoggerFactory.getLogger(MatchingServiceMultiProcessingImpl.class);
	String currencyKeyPair;
	String currencyA = null;
	String currencyB = null;
	ReentrantLock lock = new ReentrantLock();


	public MatchingServiceMultiProcessingImpl(String currencyKeyPair) {
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
		if(buys != null){
			log.info("Matching service number of buys is "+OrdersCache.buyOrders.size());
			ExecutorService executor = Executors.newFixedThreadPool(OrdersCache.buyOrders.size());
			// to speed up the process, each search spawns a thread, 
			for(CurrencyOrder buy : buys){
				if(hasFunds(buy.getPartnerId()+currencyA, buy.getAmount()) && buy.getExpirationDate().isAfter(checkDateTime) && buy.getStatus().equals(Status.UNMATCHED)){
					buy.setStatus(Status.MATCHED);
					MatchingProcessing service = new MatchingProcessingImpl(buy, currencyKeyPair, currencyA, currencyB, checkDateTime, lock);
					executor.submit(service);
				}
			}
			executor.shutdown();
		}
			
	}

}
