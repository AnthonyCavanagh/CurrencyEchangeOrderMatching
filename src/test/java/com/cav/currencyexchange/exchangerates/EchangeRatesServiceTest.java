package com.cav.currencyexchange.exchangerates;

import java.io.IOException;
import java.math.BigDecimal;

import javax.money.convert.ConversionContext;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;





public class EchangeRatesServiceTest {
	
	@Test
	public void loadRatesTest(){
		EchangeRatesService service = new EchangeRatesServiceImpl();
		try {
			service.getExchangeRates("http://www.floatrates.com/daily/gbp.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void loadExchangeRatesTest(){
		ExchangeRate rateGBPtoUSD = new ExchangeRateBuilder(ConversionContext.HISTORIC_CONVERSION)
				.setBase(Monetary.getCurrency("GBP"))
				.setTerm(Monetary.getCurrency("USD"))
				.setFactor(DefaultNumberValue.of(new BigDecimal("1.1")))
				.build();
		
		ExchangeRate rateUSDtoGB = new ExchangeRateBuilder(ConversionContext.HISTORIC_CONVERSION)
				.setBase(Monetary.getCurrency("USD"))
				.setTerm(Monetary.getCurrency("GBP"))
				.setFactor(DefaultNumberValue.of(new BigDecimal(".95")))
				.build();
		
		System.out.println(rateGBPtoUSD.getFactor());
		System.out.println();
	}
	
	@Test
	public void conversionTest(){
		MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1).create();
        CurrencyConversion conversionEUR = MonetaryConversions.getConversion("EUR");
        MonetaryAmount convertedAmountUSDtoEUR = oneDollar.with(conversionEUR);
        System.out.println(oneDollar.toString());
        System.out.println(convertedAmountUSDtoEUR.toString());
	}
	
	@Test
	public void conversion1Test(){
		// Sell  GBP/USD
		BigDecimal sellAmount = getAmount(new BigDecimal(100), new BigDecimal(1.2));
		System.out.println(sellAmount);
		MonetaryAmount amount = getAmount(new BigDecimal(100), new BigDecimal(1.2), "USD");
		System.out.println(amount.toString());
		System.out.println(amount.getNumber());
		BigDecimal usd = new BigDecimal(amount.getNumber().floatValue());
		System.out.println(usd);
		//amount.getA
	}
	
	private BigDecimal getAmount(BigDecimal amount, BigDecimal rate){
		return amount.multiply(rate);
	}
	
	private MonetaryAmount getAmount(BigDecimal amount, BigDecimal rate, String sellCurrency){
		rate = amount.multiply(rate);
		MonetaryAmount dollars = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(rate.floatValue()).create();
		return dollars;
	}
	
	

}
