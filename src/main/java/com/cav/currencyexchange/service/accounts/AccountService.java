package com.cav.currencyexchange.service.accounts;

import java.math.BigDecimal;

import com.cav.currencyexchange.entities.Account;
import com.cav.currencyexchange.models.AccountRequest;

public interface AccountService {
      void updateAccountCache(AccountRequest request);
      AccountRequest getAccountFromCache(String accountId);
      Account getAccountFromDB(String accountId);
      BigDecimal getCurrencyAmountFromCache(String key);
}
