package com.cav.currencyexchange.exchangerates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



import com.cav.currencyexchange.generated.Channel;

public class EchangeRatesServiceImpl extends AbstractExchangeRates implements EchangeRatesService{

	@Override
	public void getExchangeRates(String rateLink) throws IOException {
		URL rates = new URL(rateLink);
		URLConnection connection = rates.openConnection();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while(null != (line = br.readLine())){
			sb.append(line);
			sb.append("\n");
		}
		String xml = sb.toString();
		Channel channel = getItems(xml);
		
	}
	
	

}
