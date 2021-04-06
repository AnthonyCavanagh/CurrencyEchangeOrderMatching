package com.cav.currencyexchange.exchangerates;

import java.io.IOException;

public interface EchangeRatesService {

	
	public void getExchangeRates(String rates) throws IOException;
}
