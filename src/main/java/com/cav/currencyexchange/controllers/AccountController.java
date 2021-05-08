package com.cav.currencyexchange.controllers;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cav.currencyexchange.entities.Account;
import com.cav.currencyexchange.models.AccountFind;
import com.cav.currencyexchange.models.AccountRequest;
import com.cav.currencyexchange.models.CurrencyOrderRead;
import com.cav.currencyexchange.models.OrderRequest;
import com.cav.currencyexchange.models.SearchData;
import com.cav.currencyexchange.service.accounts.AccountService;
import com.cav.currencyexchange.service.read.ReadService;

@RestController
@RequestMapping("/accountservice")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	
	@Autowired
	ReadService readService;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);	
	
	@RequestMapping(value = "/updateAccounts/", method = RequestMethod.POST)
	public void updateAccounts(@RequestBody AccountRequest request) {
		logger.info("update Accounts "+request.toString());
		accountService.updateAccountCache(request);
	}
	
	@RequestMapping(value = "/findAccountCache/", method = RequestMethod.POST)
	public AccountRequest findAccountCache(@RequestBody AccountFind request) {
		return accountService.getAccountFromCache(request.getAccountId());
	}
	
	@RequestMapping(value = "/findAccountDB/", method = RequestMethod.POST)
	public Account findAccountDataBase(@RequestBody AccountFind request) {
		return accountService.getAccountFromDB(request.getAccountId());
	}
	

	@RequestMapping(value = "/findMatches/", method = RequestMethod.POST)
	@ResponseBody
	public SearchData findMatches(@RequestBody OrderRequest request) {
		
		String path ="C:\\orders\\matched\\Buy\\GBPUSD\\orders_20210407211955.txt";
		logger.info("findMatches "+request.toString());
		List<CurrencyOrderRead> orders = readService.readData(path);
		SearchData data = new SearchData();
		logger.info("findMatches "+orders.size());
		data.setReads(orders);
		return data;
	}

}
