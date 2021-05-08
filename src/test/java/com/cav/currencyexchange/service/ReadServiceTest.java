package com.cav.currencyexchange.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.cav.currencyexchange.models.CurrencyOrderRead;
import com.cav.currencyexchange.service.read.ReadService;
import com.cav.currencyexchange.service.read.ReadServiceImpl;

public class ReadServiceTest {

	@Test
	public void readDataTest() {
		
		ReadService service = new ReadServiceImpl();
		
		String path ="C:\\orders\\matched\\Buy\\GBPUSD\\orders_20210407211955.txt";
		List<CurrencyOrderRead> orders = service.readData(path);
		
	}
}
