package com.cav.currencyexchange.cache;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cav.currencyexchange.models.CurrencyOrder;



public class OrdersCache {
	
	// < CurrencyPair, Map <ExchangeRate, List< Order>>
	public static ConcurrentHashMap<String, CopyOnWriteArrayList<CurrencyOrder>> sellOrders = new ConcurrentHashMap <String, CopyOnWriteArrayList< CurrencyOrder>> ();
	public static ConcurrentHashMap<String, CopyOnWriteArrayList<CurrencyOrder>> buyOrders = new ConcurrentHashMap <String,CopyOnWriteArrayList<CurrencyOrder>> ();

}
