package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.service.AccountBalanceService;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;

@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {	
		
	@Autowired
	private AccountRepository accountRepository;
	
	private AccountBalanceResult output = null;
	
	@Override
	public AccountBalanceResult accountBalance(long accountID) {
		
		Optional<Account> searchResult = accountRepository.findAccountByAccountNumber(accountID);		
		
		searchResult.ifPresentOrElse( accountFound -> {
			output = AccountBalanceResult.builder()
				.sucess(true)
				.accountNumber(accountID)				
				.balance(accountFound.getBalance())
				.message(String.format(MessageStrings.SUCCESS_ACCOUNT_FOUND.get(), accountID))
				.build();				
		}, () -> {
			output = AccountBalanceResult.builder()
				.sucess(false)
				.accountNumber(accountID)
				.message(String.format(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT.get(), accountID))
				.build();
			});
		return output;		
	}
	
}
