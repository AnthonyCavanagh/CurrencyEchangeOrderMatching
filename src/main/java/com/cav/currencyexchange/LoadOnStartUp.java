package com.cav.currencyexchange;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cav.currencyexchange.cache.AccountCache;
import com.cav.currencyexchange.entities.Currency;
import com.cav.currencyexchange.repository.CurrencyRepository;
import com.cav.currencyexchange.utils.Constants;

@Component
public class LoadOnStartUp {
	
	@Autowired
    CurrencyRepository currencyRepository;
	
	private static final Logger log = LoggerFactory.getLogger(LoadOnStartUp.class);
	
	protected static final String PARTNER_A_GBP = "PartnerAGBP";
	protected static final String PARTNER_A_USD = "PartnerAUSD";
	
	protected static final String PARTNER_B_GBP = "PartnerBGBP";
	protected static final String PARTNER_B_USD = "PartnerBUSD";
	
	protected static final String PARTNER_C_GBP = "PartnerCGBP";
	protected static final String PARTNER_C_USD = "PartnerCUSD";
	
	@PostConstruct
	private void init() {
		log.info("Load on start up  ");
		Properties prop = new Properties();
		 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("application.properties");
			if (input != null) {
				try {
					prop.load(input);
					
					//Apache MS
					Constants.ACTIVEMQ_BROKER = prop.getProperty("activemq.broker.url");
					log.info("ACTIVEMQ_BROKER "+Constants.ACTIVEMQ_BROKER);
					Constants.ACTIVEMQ_USER = prop.getProperty("activemq.activemq.user");
					log.info("ACTIVEMQ_USER "+Constants.ACTIVEMQ_USER);
					Constants.ACTIVEMQ_PASSWORD = prop.getProperty("activemq.activemq.password");
					log.info("ACTIVEMQ_PASSWORD "+Constants.ACTIVEMQ_PASSWORD);
					Constants.ACTIVEMQ_TOPIC = prop.getProperty("activemq.topic");
					log.info("ACTIVEMQ_TOPIC "+Constants.ACTIVEMQ_TOPIC);
					Constants.ACTIVEMQ_CLIENT_ID = prop.getProperty("activemq.clientid");
					log.info("ACTIVEMQ_CLIENT_ID "+Constants.ACTIVEMQ_CLIENT_ID);
					Constants.RECIEVED_ORDERS = prop.getProperty("recieved.orders");
					log.info("RECIEVED_ORDERS "+Constants.RECIEVED_ORDERS);
					Constants.MATCHED_ORDERS = prop.getProperty("matched.orders");
					log.info("MATCHED_ORDERS "+Constants.MATCHED_ORDERS);
					Constants.UNMATCHED_ORDERS = prop.getProperty("unmatched.orders");
					log.info("UNMATCHED_ORDERS "+Constants.UNMATCHED_ORDERS);
					
					Constants.EXECUTOR = Executors.newFixedThreadPool(500);

					updateAccountCache();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
	
	private void updateAccountCache() {
		Iterable<Currency> iter = currencyRepository.findAll();
		iter.forEach(c->updateCache(c));
	}
	 
	 private void updateCache(Currency currency) {
			AccountCache.accounts.put(currency.getCurrencyId(), new BigDecimal(currency.getAmount()));
		}

}
