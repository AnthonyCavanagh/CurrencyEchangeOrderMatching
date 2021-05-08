package com.cav.currencyexchange.repository;


import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cav.currencyexchange.cache.AccountCache;
import com.cav.currencyexchange.entities.Account;
import com.cav.currencyexchange.entities.Currency;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountRepoTest {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
    CurrencyRepository currencyRepository;

	@Test
	public void repoAccountTest() {
		System.out.println("Run Test");
	
		Account account = new Account("PartnerA", "PartnerA");
		if(!accountRepository.existsById("PartnerA")) {
			accountRepository.save(account);
		}
		currencyRepository.save(new Currency("PartnerAUSD", "USD", "1000", account));
		account = accountRepository.findByAccountId("PartnerA");
		System.out.println(account.toString());
		currencyRepository.save(new Currency("PartnerAUSD", "USD", "2000", account));
		account = accountRepository.findByAccountId("PartnerA");
		System.out.println(account.toString());
		
		account = new Account("PartnerA", "PartnerA");
		if(!accountRepository.existsById("PartnerA")) {
			accountRepository.save(account);
		}
		currencyRepository.save(new Currency("PartnerAGBP", "GBP", "2000", account));
		account = accountRepository.findByAccountId("PartnerA");
		System.out.println(account.toString());
	}
	
	@Test
	public void repoAccountUpdateTest() {
		Account account = new Account("PartnerA", "PartnerA");
		currencyRepository.save(new Currency("PartnerARUB", "RUB", "2000", account));
		account = accountRepository.findByAccountId("PartnerA");
		System.out.println(account.toString());
	}
	
	@Test
	public void getCurrencyData() {
		Iterable<Currency> iter = currencyRepository.findAll();
		iter.forEach(c->updateCache(c));
	}
	
	private void updateCache(Currency currency) {
		AccountCache.accounts.put(currency.getCurrencyId(), new BigDecimal(currency.getAmount()));
	}
}
