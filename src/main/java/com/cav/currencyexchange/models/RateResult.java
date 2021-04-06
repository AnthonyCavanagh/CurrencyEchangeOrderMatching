package com.cav.currencyexchange.models;

import java.time.LocalDate;
import java.util.Map;

import javax.money.convert.ExchangeRate;

public class RateResult {
	private final LocalDate date;

    private final Map<String, ExchangeRate> targets;

    public RateResult(LocalDate date, Map<String, ExchangeRate> targets) {
        this.date = date;
        this.targets = targets;
    }

	public LocalDate getDate() {
		return date;
	}

	public Map<String, ExchangeRate> getTargets() {
		return targets;
	}
    
    

}
