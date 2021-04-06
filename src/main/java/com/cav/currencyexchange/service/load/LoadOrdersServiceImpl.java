package com.cav.currencyexchange.service.load;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.models.Status;
import com.cav.currencyexchange.service.ServiceBase;
import com.cav.currencyexchangebroker.generated.Orders.Order;

public class LoadOrdersServiceImpl extends ServiceBase implements LoadOrdersService {
	
	private static final Logger log = LoggerFactory.getLogger(LoadOrdersServiceImpl.class);

	private List <Order> orders = null;
	private String orderType = null;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");

	public LoadOrdersServiceImpl(List<Order> orders, String orderType) {
		super();
		this.orders = orders;
		this.orderType = orderType;
	}

	@Override
	public Object call() throws Exception {
		if(SELL.equals(orderType)){
			loadSells(orders);
		} else {
			loadBuys(orders);
		}
		return null;
	}
	
	private void loadSells(List <Order> orders){
		for(Order order :orders){
			CurrencyOrder currencyOrder = mapOrders(order);
			String key = order.getCurrencyPair();
			// replaces if value does not exist check then create new List. 
			// This is expensive but it handles the race condition of two threads accessing the map and finding
			// there is no key and both creating an new arraylist and one overwriting the other.
			OrdersCache.sellOrders.computeIfAbsent(key, k->new CopyOnWriteArrayList<CurrencyOrder>()).add(currencyOrder);
		}
	}
	
	private void loadBuys(List <Order> orders){
		for(Order order :orders){
			CurrencyOrder currencyOrder = mapOrders(order);
			String key = order.getCurrencyPair();
			OrdersCache.buyOrders.computeIfAbsent(key, k->new CopyOnWriteArrayList<CurrencyOrder>()).add(currencyOrder);
			
		}
	}
	
	private CurrencyOrder mapOrders(Order order){
		return new CurrencyOrder(order.getOrderId(),
				order.getAccount(), 
				order.getOrderType(),
				order.getCurrencyPair() ,
				new BigDecimal(order.getAmmount()),
				new BigDecimal(order.getExchangeRate()), 
				LocalDateTime.parse(order.getExpirationDate(), formatter), 
				LocalDateTime.parse(order.getPublishDate(), formatter), 
				Status.UNMATCHED);
	}

}
