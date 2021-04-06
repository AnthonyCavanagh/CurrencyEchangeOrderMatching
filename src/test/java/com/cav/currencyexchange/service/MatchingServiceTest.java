package com.cav.currencyexchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;


import org.junit.jupiter.api.Test;

import com.cav.currencyexchange.cache.AccountCache;
import com.cav.currencyexchange.cache.OrdersCache;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.models.Status;

public class MatchingServiceTest extends BaseServiceTest {

	@Test
	public void matchBasicFullOrders2Partners() {
		addAccountFullMatch2PartnersBasic();
		// GBP/USD 
		//Partner A is buying 100 USD for GBP rate 1.25
		//Partner B is selling 100 USD for GBP rate 1.25
        String [] xmlOrders = new String [] {"matching/USDGBPBasic2Partners.xml"};
		loadOrders(xmlOrders);
		matchOrders();
				
		assertEquals(new BigDecimal(4900).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(5125).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(5100).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(4875).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					assertTrue(Status.REMOVE.equals(order.getStatus()));
				}
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					assertTrue(Status.REMOVE.equals(order.getStatus()));
				}
			}
		}
		
	}
	
	@Test
	public void matchBasicFullOrders3Partners() {
		addAccountFullMatch3PartnersBasic();
		// GBP/USD 
		//Partner A is buying 100 USD for GBP rate 1.25 *2
		//Partner B is selling 100 USD for GBP rate 1.25
		//Partner C is selling 100 USD for GBP rate 1.25
        String [] xmlOrders = new String [] {"matching/USDGBPBasic3Partners.xml"};
		loadOrders(xmlOrders);
		matchOrders();
		System.out.println(AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		System.out.println(AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		System.out.println(AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		System.out.println(AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		System.out.println(AccountCache.accounts.get(PARTNER_C_GBP).stripTrailingZeros());
		System.out.println(AccountCache.accounts.get(PARTNER_C_USD).stripTrailingZeros());
				
		assertEquals(new BigDecimal(4800).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(5250).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(5100).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(4875).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(5100).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_C_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(4875).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_C_USD).stripTrailingZeros());
		
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					assertTrue(Status.REMOVE.equals(order.getStatus()));
				}
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					assertTrue(Status.REMOVE.equals(order.getStatus()));
				}
			}
		}
	}
	
	@Test
	public void match50OrdersFullOrders2Partners() {
		addAccountFullMatch2PartnersBasic();
		// GBP/USD 
		//Partner A is buying 100 USD for GBP rate 1 * 50
		//Partner B is selling 100 USD for GBP rate 1 * 50
        String [] xmlOrders = new String [] {"matching/USDGBP50rders2Partners.xml"};
		loadOrders(xmlOrders);
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
					assertEquals(50, orders.size());
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				if(orders != null) {
					assertEquals(50, orders.size());
			}
			}
		}
		matchOrders();
		
		System.out.println(AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros().setScale(2));
		
		assertEquals(new BigDecimal(1000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(10000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(9000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(0).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		
		int buyRemove = 0;
		int buyUnMatched = 0;
		
		int sellRemove = 0;
		int sellUnMatched = 0;
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						buyRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						buyUnMatched++;
					}
				}
			}
		}
		
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						sellRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						sellUnMatched++;
					}
				}
			}
		}
		
		assertEquals(40, buyRemove);
		assertEquals(10, buyUnMatched);
		assertEquals(40, sellRemove);
		assertEquals(10, sellUnMatched);
	}
	
	@Test
	public void match1000OrdersFullOrders2Partners() {
		addAccountFullMatch2Partners();
		// GBP/USD 
		//Partner A is buying 100 USD for GBP rate 1 * 1000
		//Partner B is selling 100 USD for GBP rate 1 * 1000
        String [] xmlOrders = new String [] {"matching/USDGBP1000Orders2Partners.xml"};
		loadOrders(xmlOrders);
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
				assertEquals(1000, orders.size());
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				assertEquals(1000, orders.size());
			}
		}
		matchOrders();
		
		System.out.println(AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros().setScale(2));
		
		assertEquals(new BigDecimal(900000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(1125000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(1100000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(875000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					assertTrue(Status.REMOVE.equals(order.getStatus()));
				}
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					assertTrue(Status.REMOVE.equals(order.getStatus()));
				}
			}
		}

	}
	
	@Test
	public void match10000OrdersFullOrders2Partners() {
		addAccountFullMatch2Partners();
		// GBP/USD 
		//Partner A is buying 100 USD for GBP rate 1 * 1000
		//Partner B is selling 100 USD for GBP rate 1 * 1000
        String [] xmlOrders = new String [] {"matching/USDGBP1000Orders2Partners.xml","matching/USDGBP1000Orders2Partners2.xml","matching/USDGBP1000Orders2Partners3.xml","matching/USDGBP1000Orders2Partners4.xml","matching/USDGBP1000Orders2Partners5.xml","matching/USDGBP1000Orders2Partners6.xml","matching/USDGBP1000Orders2Partners7.xml","matching/USDGBP1000Orders2Partners8.xml","matching/USDGBP1000Orders2Partners9.xml","matching/USDGBP1000Orders2Partners10.xml"};
		loadOrders(xmlOrders);
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
				assertEquals(10000, orders.size());
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				assertEquals(10000, orders.size());
			}
		}
		long startTime = System.currentTimeMillis();
		matchOrders();
		long finishTime = System.currentTimeMillis();
		System.out.println("Match orders 2D List took " + (finishTime - startTime) + " ms");
		
		System.out.println(AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros().setScale(2));
		
		assertEquals(new BigDecimal(200000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(2000000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(1800000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(0).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		
		int buyRemove = 0;
		int buyUnMatched = 0;
		
		int sellRemove = 0;
		int sellUnMatched = 0;
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						buyRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						buyUnMatched++;
					}
				}
			}
		}
		
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						sellRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						sellUnMatched++;
					}
				}
			}
		}
		assertEquals(8000, buyRemove);
		assertEquals(2000, buyUnMatched);
		assertEquals(8000, sellRemove);
		assertEquals(2000, sellUnMatched);
	}
	
	//Multiprocessing 
	
	@Test
	public void match50OrdersFullOrders2PartnersMultiProcessing() {
		addAccountFullMatch2PartnersBasic();
		// GBP/USD 
		//Partner A is buying 100 USD for GBP rate 1 * 50
		//Partner B is selling 100 USD for GBP rate 1 * 50
        String [] xmlOrders = new String [] {"matching/USDGBP50rders2Partners.xml"};
		loadOrders(xmlOrders);
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
					assertEquals(50, orders.size());
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				if(orders != null) {
					assertEquals(50, orders.size());
			}
			}
		}
		matchOrdersMultiProccessing();
		
		System.out.println(AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros().setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros().setScale(2));
		
		assertEquals(new BigDecimal(1000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(10000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(9000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(0).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		
		int buyRemove = 0;
		int buyUnMatched = 0;
		
		int sellRemove = 0;
		int sellUnMatched = 0;
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						buyRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						buyUnMatched++;
					}
				}
			}
		}
		
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						sellRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						sellUnMatched++;
					}
				}
			}
		}
		
		assertEquals(40, buyRemove);
		assertEquals(10, buyUnMatched);
		assertEquals(40, sellRemove);
		assertEquals(10, sellUnMatched);
	}
	
	@Test
	public void match10000OrdersFullOrders2PartnersMultiProcessing() {
		addAccountFullMatch2Partners();
		// GBP/USD 
		//Partner A is buying 100 USD for GBP rate 1 * 1000
		//Partner B is selling 100 USD for GBP rate 1 * 1000
        String [] xmlOrders = new String [] {"matching/USDGBP1000Orders2Partners.xml","matching/USDGBP1000Orders2Partners2.xml","matching/USDGBP1000Orders2Partners3.xml","matching/USDGBP1000Orders2Partners4.xml","matching/USDGBP1000Orders2Partners5.xml","matching/USDGBP1000Orders2Partners6.xml","matching/USDGBP1000Orders2Partners7.xml","matching/USDGBP1000Orders2Partners8.xml","matching/USDGBP1000Orders2Partners9.xml","matching/USDGBP1000Orders2Partners10.xml"};
		loadOrders(xmlOrders);
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> sellEntry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = sellEntry.getValue();
			if(orders != null) {
				assertEquals(10000, orders.size());
			}
		}
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				assertEquals(10000, orders.size());
			}
		}
		
		long startTime = System.currentTimeMillis();
		matchOrdersMultiProccessing();
		long finishTime = System.currentTimeMillis();
		System.out.println("Match orders multi threaded search took " + (finishTime - startTime) + " ms");
		
		System.out.println(AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros().setScale(2).setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros().setScale(2).setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros().setScale(2).setScale(2));
		System.out.println(AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros().setScale(2).setScale(2));
		
		assertEquals(new BigDecimal(200000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(2000000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_A_USD).stripTrailingZeros());
		assertEquals(new BigDecimal(1800000).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_GBP).stripTrailingZeros());
		assertEquals(new BigDecimal(0).stripTrailingZeros(), AccountCache.accounts.get(PARTNER_B_USD).stripTrailingZeros());
		
		int buyRemove = 0;
		int buyUnMatched = 0;
		
		int sellRemove = 0;
		int sellUnMatched = 0;
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.buyOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						buyRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						buyUnMatched++;
					}
				}
			}
		}
		
		for(Entry<String, CopyOnWriteArrayList<CurrencyOrder>> entry : OrdersCache.sellOrders.entrySet()){
			CopyOnWriteArrayList<CurrencyOrder> orders = entry.getValue();
			if(orders != null) {
				for(CurrencyOrder order :orders) {
					if(Status.REMOVE.equals(order.getStatus())) {
						sellRemove++;
					} else if(Status.UNMATCHED.equals(order.getStatus())) {
						sellUnMatched++;
					}
				}
			}
		}
		System.out.println("buyRemove "+buyRemove);
		System.out.println("buyUnMatched "+buyUnMatched);
		System.out.println("sellRemove "+sellRemove);
		System.out.println("sellUnMatched "+sellUnMatched);
		assertEquals(8000, buyRemove);
		assertEquals(2000, buyUnMatched);
		assertEquals(8000, sellRemove);
		assertEquals(2000, sellUnMatched);
	}
}
