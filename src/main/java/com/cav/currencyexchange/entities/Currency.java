package com.cav.currencyexchange.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "currencies")
public class Currency {
	
	@Id
	String currencyId;
	String currencyType;
	String amount;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "acount_currency_id", nullable = false)
	Account account;

	public Currency() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Currency(String currencyId, String currencyType, String amount, Account account) {
		super();
		this.currencyId = currencyId;
		this.currencyType = currencyType;
		this.amount = amount;
		this.account = account;
	}
	
	
	
	public String getCurrencyId() {
		return currencyId;
	}

	public String getCurrencyType() {
		return currencyType;
	}


	public String getAmount() {
		return amount;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	@Override
	public String toString() {
		return "Currency [currencyId=" + currencyId + ", currencyType=" + currencyType + ", amount=" + amount + "]";
	}

}
