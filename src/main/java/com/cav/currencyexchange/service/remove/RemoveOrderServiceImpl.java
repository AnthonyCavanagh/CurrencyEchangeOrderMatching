package com.cav.currencyexchange.service.remove;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.models.Status;
import com.cav.currencyexchange.service.ServiceBase;
import com.cav.currencyexchange.service.write.WriteService;
import com.cav.currencyexchange.service.write.WriteServiceImpl;
import com.cav.currencyexchange.utils.Constants;

public class RemoveOrderServiceImpl extends ServiceBase implements RemoveOrderService {

	private static final Logger log = LoggerFactory.getLogger(RemoveOrderServiceImpl.class);
	String currencyKeyPair;
	String orderType;

	public RemoveOrderServiceImpl(String currencyKeyPair, String orderType) {
		super();
		this.currencyKeyPair = currencyKeyPair;
		this.orderType = orderType;
	}

	@Override
	public Object call() throws Exception {
		if(SELL.equals(orderType)) {
			processOrdersSell(currencyKeyPair);
		} else {
			processOrdersBuy(currencyKeyPair);
		}
		return null;
	}
	
	private void processOrdersSell(String currencyKeyPair) {
		List<CurrencyOrder> orders = OrdersCache.sellOrders.get(currencyKeyPair);
		if(orders != null) {
			//log.info("Remove Orders Sell Cache "+orders.size());
			List <CurrencyOrder> matchedOrders = new ArrayList<CurrencyOrder>();
			List <CurrencyOrder> unmatchedOrders = new ArrayList<CurrencyOrder>();
			LocalDateTime checkDateTime = LocalDateTime.now();
			for(CurrencyOrder order : orders){
				if (order.getExpirationDate().isBefore(checkDateTime))  {
					unmatchedOrders.add(order);
					orders.remove(order);
				} else if (order.getStatus().equals(Status.REMOVE)) {
					matchedOrders.add(order);
					orders.remove(order);
				}
			}
			writeToFiles(matchedOrders, unmatchedOrders, checkDateTime, currencyKeyPair, this.orderType);
		}
	}
	
	private void processOrdersBuy(String currencyKeyPair) {
		List<CurrencyOrder> orders = OrdersCache.buyOrders.get(currencyKeyPair);
		if(orders != null) {
			//log.info("Remove Orders Buy Cache "+orders.size());
			List <CurrencyOrder> matchedOrders = new ArrayList<CurrencyOrder>();
			List <CurrencyOrder> unmatchedOrders = new ArrayList<CurrencyOrder>();
			LocalDateTime checkDateTime = LocalDateTime.now();
			for(CurrencyOrder order : orders){
				if (order.getExpirationDate().isBefore(checkDateTime))  {
					unmatchedOrders.add(order);
					orders.remove(order);
				} else if (order.getStatus().equals(Status.REMOVE)) {
					matchedOrders.add(order);
					orders.remove(order);
				}
			}
			writeToFiles(matchedOrders, unmatchedOrders, checkDateTime, currencyKeyPair, this.orderType);
		}
		
	}
	
	private void writeToFiles(List <CurrencyOrder> matchedOrders, 
			List <CurrencyOrder> unmatchedOrders, LocalDateTime recievedTime, String currencyKeyPair, String orderType) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		WriteService service = null;
		if(!matchedOrders.isEmpty()) {
			service = new WriteServiceImpl(matchedOrders, recievedTime, Constants.MATCHED_ORDERS, currencyKeyPair, orderType);
			executor.submit(service);
		}
		
		if(!unmatchedOrders.isEmpty()) {
			service = new WriteServiceImpl(unmatchedOrders, recievedTime, Constants.UNMATCHED_ORDERS, currencyKeyPair, orderType);
			executor.submit(service);
		}
		executor.shutdown();
	}
	
}
