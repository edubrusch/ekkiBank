package com.eduardo.ekki.ekkiTransfer.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardo.ekki.ekkiTransfer.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	public Set<Account> findByAccountNumber(String accountNumber);

}
