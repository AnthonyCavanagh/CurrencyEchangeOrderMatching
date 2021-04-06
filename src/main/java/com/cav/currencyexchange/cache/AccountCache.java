package com.cav.currencyexchange.cache;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;



public class AccountCache {

	public static  ConcurrentHashMap<String, BigDecimal> accounts = new ConcurrentHashMap <String, BigDecimal> ();
}
