package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.data.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.service.AccountBalanceService;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;

@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {	
		
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public AccountBalanceResult accountBalance(long accountID) {
		
		Optional<Account> searchResult = accountRepository.findAccountByAccountNumber(accountID);		
		
		if(searchResult.isEmpty()) {
			return AccountBalanceResult.processFailure(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT, accountID);			
		}
		return AccountBalanceResult.processSuccess(MessageStrings.SUCCESS_ACCOUNT_FOUND, accountID);
		
	}
	
}
