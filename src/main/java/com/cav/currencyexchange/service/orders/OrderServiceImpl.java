package com.cav.currencyexchange.service.orders;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.cav.currencyexchange.service.ServiceBase;
import com.cav.currencyexchange.service.load.LoadOrdersService;
import com.cav.currencyexchange.service.load.LoadOrdersServiceImpl;
import com.cav.currencyexchangebroker.generated.Orders;
import com.cav.currencyexchangebroker.generated.Orders.Order;


public class OrderServiceImpl extends ServiceBase implements OrderService {

	private InputStream stream = null;

	public OrderServiceImpl(InputStream stream) {
		super();
		this.stream = stream;
	}
	
	

	@Override
	public Object call() throws Exception {
           loadOrders(stream);
		return null;
	}


	private void loadOrders(InputStream stream){
		List<Order> sellOrders = new ArrayList<Order>();
		List<Order> buyOrders = new ArrayList<Order>();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Orders orders = streamOrders(stream);
		for(Order order :orders.getOrder()){
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
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

	
	
	
}
