package com.cav.currencyexchange.service.processorders;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.service.ServiceBase;
import com.cav.currencyexchange.service.load.LoadOdersLiveMessaging;
import com.cav.currencyexchange.service.load.LoadOdersLiveMessagingImpl;
import com.cav.currencyexchange.service.matching.MatchingServiceMultiProcessingImpl;
import com.cav.currencyexchange.service.write.WriteService;
import com.cav.currencyexchange.service.write.WriteServiceImpl;
import com.cav.currencyexchangebroker.generated.Orders.Order;

@Service
public class ProcessOrdersServicesImpl extends ServiceBase  implements ProcessOrdersServices {
	
	private List <Order> orders = null;

	public ProcessOrdersServicesImpl(List <Order> orders) {
		super();
		this.orders =orders;
	}

	@Override
	public Object call() throws Exception {
		processLiveMessages(orders);
		return null;
	}
	
	/**
	 * spawans a process for write orders, store on the cache and for each buy search for each currencypair
	 * @param messages
	 */
	private void processLiveMessages(List<Order> messages) {
		MatchingServiceMultiProcessingImpl matching = null;
		LocalDateTime recievedDate = LocalDateTime.now();
		ExecutorService executor = Executors.newFixedThreadPool(2+OrdersCache.buyOrders.size());
		WriteService write = new WriteServiceImpl(messages, recievedDate);
		executor.submit(write);
		LoadOdersLiveMessaging load = new LoadOdersLiveMessagingImpl(messages);
		executor.submit(load);
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> buyEntry : OrdersCache.buyOrders.entrySet()){
			matching = new MatchingServiceMultiProcessingImpl(buyEntry.getKey());
			executor.submit(matching);
		}
		executor.shutdown();
	}

	

}
