package com.cav.currencyexchange.service.load;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.service.ServiceBase;
import com.cav.currencyexchangebroker.generated.Orders.Order;

public class LoadOdersLiveMessagingImpl extends ServiceBase  implements LoadOdersLiveMessaging {
	
	private static final Logger log = LoggerFactory.getLogger(LoadOdersLiveMessagingImpl.class);
	List<Order> messages = null;
	
	public LoadOdersLiveMessagingImpl(List<Order> messages) {
		super();
		this.messages = messages;
	}

	@Override
	public Object call() throws Exception {
		loadOrders(messages);
		return null;
	}
	
	protected void loadOrders(List<Order> messages){
		List<Order> sellOrders = new ArrayList<Order>();
		List<Order> buyOrders = new ArrayList<Order>();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		for(Order order : messages){
			if(SELL.equals(order.getOrderType())){
				sellOrders.add(order);
			} else {
				buyOrders.add(order);
			}
		}
		
		LoadOrdersService service = new LoadOrdersServiceImpl(sellOrders, SELL);
		executor.submit(service);
		service = new LoadOrdersServiceImpl(buyOrders, BUY);
		executor.submit(service);
		executor.shutdown();
		
	}

}
