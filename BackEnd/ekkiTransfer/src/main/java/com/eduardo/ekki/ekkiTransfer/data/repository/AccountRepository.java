package com.eduardo.ekki.ekkiTransfer.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardo.ekki.ekkiTransfer.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	public Optional<Account> findAccountByAccountNumber(long accountNumber);

}
