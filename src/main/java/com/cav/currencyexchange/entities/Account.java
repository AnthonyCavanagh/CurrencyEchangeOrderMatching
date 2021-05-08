package com.cav.currencyexchange.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	
	@Id
	String accountId;
	String name;
	
	@OneToMany(mappedBy = "account", 
            cascade = CascadeType.ALL)
	Set <Currency> currencies = new HashSet<Currency>();
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(String accountId, String name) {
		super();
		this.accountId = accountId;
		this.name = name;
	}

	public Set<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Set<Currency> currencies) {
		this.currencies = currencies;
	}

	

	public String getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", name=" + name + ", currencies=" + currencies + "]";
	}

}
