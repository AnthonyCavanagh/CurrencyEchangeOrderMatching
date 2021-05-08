package com.cav.currencyexchange.models;

import java.util.ArrayList;
import java.util.List;

public class AccountRequest {
    private String accountName;
    
    private List <AccountAmount> accountAmounts = new ArrayList<AccountAmount>();

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public List<AccountAmount> getAccountAmounts() {
		return accountAmounts;
	}

	public void setAccountAmounts(List<AccountAmount> accountAmounts) {
		this.accountAmounts = accountAmounts;
	}

	@Override
	public String toString() {
		return "AccountRequest [accountName=" + accountName + ", accountAmounts=" + accountAmounts + "]";
	}

	
 
}
