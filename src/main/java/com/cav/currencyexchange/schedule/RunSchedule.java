package com.cav.currencyexchange.schedule;

import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.service.remove.RemoveOrderServiceImpl;

@Component
public class RunSchedule {
	
	private static final Logger log = LoggerFactory.getLogger(RunSchedule.class);
	
	@Scheduled(fixedRateString ="${schedule.remove.orders}")
	public void removeOrders() {
		ExecutorService executor = Executors.newFixedThreadPool(10);
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
		/*
			try {
				executor.awaitTermination(2, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
	}

}
