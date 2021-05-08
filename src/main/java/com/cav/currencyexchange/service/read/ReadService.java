package com.cav.currencyexchange.service.read;

import java.util.List;

import com.cav.currencyexchange.models.CurrencyOrderRead;

public interface ReadService {
	
	public List<CurrencyOrderRead> readData(String path);

}
