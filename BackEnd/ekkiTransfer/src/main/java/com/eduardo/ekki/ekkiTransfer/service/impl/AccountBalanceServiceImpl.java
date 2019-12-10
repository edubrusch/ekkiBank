package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStringsEnum;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.service.AccountBalanceService;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;

@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {	
		
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public AccountBalanceResult accountBalance(long accountID) {
		
		Optional<Account> searchResult = accountRepository.findAccountByAccountNumber(accountID);		
		
		if(searchResult.isPresent()) {
			return AccountBalanceResult.builder()
					.sucess(true)
					.accountNumber(accountID)
					.balance(searchResult.get().getBalance())
					.credit(searchResult.get().getCredit())
					.message(String.format(MessageStringsEnum.SUCCESS_ACCOUNT_FOUND.get(), accountID))
					.build();
		}
		
		return AccountBalanceResult.builder()
				.sucess(false)
				.accountNumber(accountID)
				.message(String.format(MessageStringsEnum.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT.get(), accountID))
				.build();
		
	}
	
}
