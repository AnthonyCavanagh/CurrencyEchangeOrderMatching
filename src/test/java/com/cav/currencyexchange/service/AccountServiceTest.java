package com.cav.currencyexchange.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cav.currencyexchange.models.AccountAmount;
import com.cav.currencyexchange.models.AccountRequest;
import com.cav.currencyexchange.service.accounts.AccountService;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {
	
	@Autowired
	AccountService accountService;
	
	@Test
	public void updateAccountTest() {
		System.out.println("Run Test");
		AccountRequest accoutRequest = new AccountRequest();
		accoutRequest.setAccountName("PartnerC");
		AccountAmount accountAmount = new AccountAmount();
		accountAmount.setCurrency("GBP");
		accountAmount.setAmount("1000000");
		accoutRequest.getAccountAmounts().add(accountAmount);
		accountAmount = new AccountAmount();
		accountAmount.setCurrency("USD");
		accountAmount.setAmount("1000000");
		accoutRequest.getAccountAmounts().add(accountAmount);
		accountAmount = new AccountAmount();
		accountAmount.setCurrency("EUR");
		accountAmount.setAmount("1000000");
		accoutRequest.getAccountAmounts().add(accountAmount);
		accountAmount = new AccountAmount();
		accountAmount.setCurrency("YEN");
		accountAmount.setAmount("1000000");
		accoutRequest.getAccountAmounts().add(accountAmount);
		accountService.updateAccountCache(accoutRequest);
	}

}
