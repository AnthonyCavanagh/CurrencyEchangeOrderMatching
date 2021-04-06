package com.cav.currencyexchange.exchangerates;

import java.math.BigDecimal;
import java.math.RoundingMode;


import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;

public class PrecisionPerformanceMonetaryAmounts {

	
	@Test
	public void convertGBPintoUSDPrecionBigDecimal() {
		BigDecimal pounds = new BigDecimal(100);
		BigDecimal rates = new BigDecimal(1.38);
		BigDecimal dollars = pounds.multiply(rates).setScale(2, RoundingMode.HALF_EVEN);
		System.out.println(dollars);
		
	}
	
	@Test
	public void convertGBPintoUSDPrecionMonetaryAmount() {
		MonetaryAmount initialValue = Monetary
                .getDefaultAmountFactory()
                .setNumber(100f)
                .setCurrency("USD")
                .create();
		
		initialValue = initialValue.multiply(1.38);
		System.out.println(initialValue.getNumber().floatValue());
		
	}
	
	
	@Test
	public void convertGBPintoUSDPrecionMoney() {
		BigDecimal pounds = new BigDecimal(100);
		BigDecimal rate = new BigDecimal(1.38);
		Money moneyGBP = Money.of(CurrencyUnit.GBP, pounds);
		Money moneyUSD = moneyGBP.convertedTo(CurrencyUnit.USD, rate, RoundingMode.HALF_EVEN);
		System.out.println(moneyUSD.getAmount());
		
	}
	
	@Test
	public void convertGBPintoJPYPrecionBigDecimal() {
		BigDecimal pounds = new BigDecimal(100);
		BigDecimal rates = new BigDecimal(149.56);
		BigDecimal dollars = pounds.multiply(rates).setScale(2, RoundingMode.HALF_EVEN);
		System.out.println(dollars);
		
	}
	
	@Test
	public void convertGBPintoJPYPrecionMonetaryAmount() {
		MonetaryAmount initialValue = Monetary
                .getDefaultAmountFactory()
                .setNumber(100f)
                .setCurrency("JPY")
                .create();
		
		initialValue = initialValue.multiply(149.56);
		System.out.println(initialValue.getNumber().floatValue());
		
	}

	@Test
	public void convertGBPintoJPYPrecionMoney() {
		BigDecimal pounds = new BigDecimal(100);
		BigDecimal rate = new BigDecimal(149.56);
		Money moneyGBP = Money.of(CurrencyUnit.GBP, pounds);
		Money moneyUSD = moneyGBP.convertedTo(CurrencyUnit.JPY, rate, RoundingMode.HALF_EVEN);
		System.out.println(moneyUSD.getAmount());
		
	}
	
	@Test
	public void convertGBPintoEURPrecionBigDecimal() {
		BigDecimal pounds = new BigDecimal(100);
		BigDecimal rates = new BigDecimal(1.16);
		BigDecimal euro = pounds.multiply(rates).setScale(2, RoundingMode.HALF_EVEN);
		System.out.println(euro);
		
	}
	
	@Test
	public void convertGBPintoEURPrecionMonetaryAmount() {
		MonetaryAmount initialValue = Monetary
                .getDefaultAmountFactory()
                .setNumber(100f)
                .setCurrency("EUR")
                .create();
		
		initialValue = initialValue.multiply(1.16);
		System.out.println(initialValue.getNumber().floatValue());
		
	}

	@Test
	public void convertGBPintoEURPrecionMoney() {
		BigDecimal pounds = new BigDecimal(100);
		BigDecimal rate = new BigDecimal(1.16);
		Money moneyGBP = Money.of(CurrencyUnit.GBP, pounds);
		Money moneyUSD = moneyGBP.convertedTo(CurrencyUnit.EUR, rate, RoundingMode.HALF_EVEN);
		System.out.println(moneyUSD.getAmount());
	}
	
	@Test
	public void convertGBPintoUSDPerformanceBigDecimal() {
		long startTime = System.currentTimeMillis();
		for(int index=0; index < 10000000; index++) {
			BigDecimal pounds = new BigDecimal(100);
			BigDecimal rates = new BigDecimal(1.38);
			BigDecimal dollars = pounds.multiply(rates).setScale(2, RoundingMode.HALF_EVEN);
		}
		long finishTime = System.currentTimeMillis();
		System.out.println("That took: " + (finishTime - startTime) + " ms");
		
	}
	
	@Test
	public void convertGBPintoUSDPerformanceMoney() {
		long startTime = System.currentTimeMillis();
		for(int index=0; index < 10000000; index++) {
			BigDecimal pounds = new BigDecimal(100);
			BigDecimal rate = new BigDecimal(1.38);
			Money moneyGBP = Money.of(CurrencyUnit.GBP, pounds);
			Money moneyUSD = moneyGBP.convertedTo(CurrencyUnit.USD, rate, RoundingMode.HALF_EVEN);
		}
		long finishTime = System.currentTimeMillis();
		System.out.println("That took: " + (finishTime - startTime) + " ms");
		
	}
}
