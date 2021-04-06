package com.cav.currencyexchange.service;


import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;

import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;

public class OrderServiceTest extends BaseServiceTest{
	
	
	@Test
	public void testLoadOrders(){
		String [] xmlOrders = new String [] {"xml/EUGBP.xml"};
		
		loadOrders(xmlOrders);
		System.out.println("End Order Loading");
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> buyEntry : OrdersCache.buyOrders.entrySet()){
			System.out.println("Buy "+buyEntry.getKey()+" orders size "+buyEntry.getValue().size());
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			System.out.println("Sell "+sellEntry.getKey()+" orders size "+sellEntry.getValue().size());
		}
		
	}
	@Test
	public void testLoadOrdersRaceCondition(){
		String [] xmlOrders = new String [] {"xml/EUGBP.xml", "xml/EUGBP2.xml"};
		
		loadOrders(xmlOrders);
		System.out.println("End Order Loading");
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> buyEntry : OrdersCache.buyOrders.entrySet()){
			System.out.println("Buy "+buyEntry.getKey()+" orders size "+buyEntry.getValue().size());
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			System.out.println("Sell "+sellEntry.getKey()+" orders size "+sellEntry.getValue().size());
		}
		
	}
	
	

}
