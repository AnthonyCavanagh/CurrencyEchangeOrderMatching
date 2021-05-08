package com.cav.currencyexchange.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cav.currencyexchange.entities.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository <Currency, String> {
	

}
