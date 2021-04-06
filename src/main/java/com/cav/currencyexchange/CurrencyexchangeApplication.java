package com.cav.currencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJms
public class CurrencyexchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyexchangeApplication.class, args);
	}

}
