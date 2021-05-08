package com.cav.currencyexchange.models;

public class AccountAmount {
    private String currency;

    private String amount;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "AccountAmount [currency=" + currency + ", amount=" + amount + "]";
	}   
    
}