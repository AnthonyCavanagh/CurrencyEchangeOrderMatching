package com.cav.currencyexchange.service.matching;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.models.Status;
import com.cav.currencyexchange.service.ServiceBase;

public class MatchingProcessingImpl extends ServiceBase implements MatchingProcessing {

	private static final Logger log = LoggerFactory.getLogger(MatchingProcessingImpl.class);
	
	
	CurrencyOrder buy = null;
	String currencyKeyPair = null;
	String currencyA = null;
	String currencyB = null;
	LocalDateTime checkDateTime = null;
	ReentrantLock lock = null;

	
	

	public MatchingProcessingImpl(CurrencyOrder buy, String currencyKeyPair, String currencyA, String currencyB,
			LocalDateTime checkDateTime, ReentrantLock lock) {
		super();
		this.buy = buy;
		this.currencyKeyPair = currencyKeyPair;
		this.currencyA = currencyA;
		this.currencyB = currencyB;
		this.checkDateTime = checkDateTime;
		this.lock = lock;
	}

	@Override
	public Object call() throws Exception {
		matchLocked(buy);
		return null;
	}
	
	/**
	 * Matches a buy with a sell 
	 * @param buy
	 */
	private void matchLocked(CurrencyOrder buy) {
		buy.setStatus(Status.MATCHED);
		CopyOnWriteArrayList<CurrencyOrder> sells = OrdersCache.sellOrders.get(currencyKeyPair);
		for(CurrencyOrder sell : sells){
			BigDecimal sellAmount = getAmount(buy.getAmount(), buy.getExchangeRate());
			if(hasFunds(sell.getPartnerId() +currencyB, sellAmount)  && sell.getExpirationDate().isAfter(checkDateTime) && sell.getStatus().equals(Status.UNMATCHED)){
				log.info("Lock is set to "+lock.isLocked());
					lock.lock();
					log.info("Match orders for sell "+sell.getStatus());
					 //Potential race condition CurrencyOrder could be processed while lock is being acquired
					 if(sell.getStatus().equals(Status.UNMATCHED)) {
						if(matchFull(buy, sell, currencyA, currencyB, sellAmount)) {
							log.info("Matched order for buy "+buy.toString());
							log.info("Matched order for buy "+sell.toString());
							lock.unlock();
							break;
						} 
					} 
					log.info("unlock lock ");
					lock.unlock();
			} else {
				buy.setStatus(Status.UNMATCHED);
			}
		}
	}

}
