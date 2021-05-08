package com.cav.currencyexchange.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cav.currencyexchange.entities.Account;



@Repository
public interface AccountRepository extends CrudRepository <Account, String> {
	
	@Query("select a from Account a JOIN FETCH a.currencies c where (a.accountId = :accountId)")
	Account findByAccountId(@Param("accountId")String accountId);

}
