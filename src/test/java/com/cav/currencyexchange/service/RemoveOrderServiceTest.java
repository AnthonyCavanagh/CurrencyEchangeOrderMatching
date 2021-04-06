package com.cav.currencyexchange.service;

import org.junit.jupiter.api.Test;

public class RemoveOrderServiceTest extends BaseServiceTest {
	
	@Test
	public void RemoveOrdersTest(){
		String [] xmlOrders = new String [] {"xml/EUGBPFiltrerd.xml"};
		loadOrders(xmlOrders);
		removeOrders();
	}
	
	@Test
	public void RemoveOrdersRaceConditionTest(){
		String [] xmlOrders = new String [] {"xml/EUGBPFiltrerd.xml"};
		loadOrders(xmlOrders);
		loadRemoveOrders(xmlOrders);
		
	}
	
	@Test
	public void RemoveOrdersNullTest(){
		removeOrders();
	}

}
