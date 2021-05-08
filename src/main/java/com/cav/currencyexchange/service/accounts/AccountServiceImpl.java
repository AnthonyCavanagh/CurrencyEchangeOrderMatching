package com.cav.currencyexchange.service.accounts;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cav.currencyexchange.cache.AccountCache;
import com.cav.currencyexchange.entities.Account;
import com.cav.currencyexchange.entities.Currency;
import com.cav.currencyexchange.models.AccountAmount;
import com.cav.currencyexchange.models.AccountRequest;
import com.cav.currencyexchange.repository.AccountRepository;
import com.cav.currencyexchange.repository.CurrencyRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
    CurrencyRepository currencyRepository;

	@Override
	public void updateAccountCache(AccountRequest request) {
		// TODO Auto-generated method stub
		String accountName = request.getAccountName();
		List<AccountAmount> accounts = request.getAccountAmounts();
		Account account = new Account(accountName, accountName);
		if(!accountRepository.existsById(request.getAccountName())) {
			accountRepository.save(account);
		}
		
		for(AccountAmount accountAmount : accounts) {
			BigDecimal amount = new BigDecimal(accountAmount.getAmount());
			synchronized(AccountCache.accounts) {
				String key = accountName+accountAmount.getCurrency();
				AccountCache.accounts.merge(key, amount, BigDecimal::add);
				amount = getCurrencyAmountFromCache(key);
				currencyRepository.save(new Currency(key, accountAmount.getCurrency(), amount.toString(), account));
			}
		}
	}

	@Override
	public BigDecimal getCurrencyAmountFromCache(String key) {
		return AccountCache.accounts.get(key);
	}

	@Override
	public AccountRequest getAccountFromCache(String accountId) {
		AccountRequest account = new AccountRequest();
		account.setAccountName(accountId);
		for(Entry<String, BigDecimal> entrySet : AccountCache.accounts.entrySet()) {
			String key = entrySet.getKey();
			if(key.contains(accountId)) {
				String[] fields = key.split(accountId);
				AccountAmount accountAmount = new AccountAmount();
				accountAmount.setCurrency(fields[1]);
				accountAmount.setAmount(entrySet.getValue().toString());
				account.getAccountAmounts().add(accountAmount);
			}
		}
		return account;
	}

	@Override
	public Account getAccountFromDB(String accountId) {
		return accountRepository.findByAccountId(accountId);
	}

}
