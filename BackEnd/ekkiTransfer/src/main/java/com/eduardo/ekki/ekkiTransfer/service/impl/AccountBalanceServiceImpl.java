package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.data.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.domain.Account;
import com.eduardo.ekki.ekkiTransfer.service.AccountBalanceService;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;

@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {	
	
	private AccountRepository accountRepository;
	
	@Override
	public AccountBalanceResult accountBalance(String accountID) {
		
		Set<Account> searchResult =  accountRepository.findByAccountNumber(accountID);
		
		if(searchResult.isEmpty()||searchResult == null) {
			if(accountID == null) {
				accountID = "null";
			}
			throw new RuntimeException(String.format(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT.get(), accountID));
		}		

		AccountBalanceResult output = new AccountBalanceResult();
		Account account = searchResult.iterator().next();			
		
		output.setSucess(true);
		output.setAccountNumber(account.getAccountNumber());
		output.setBalance(account.getBalance());
		
		
		return output;
	}
	
}
