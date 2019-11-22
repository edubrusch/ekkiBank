package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferServiceImpl implements TransferService{
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public TransferResult transferCash(long source, long recipient, BigDecimal amount) {
				
		Optional<Account> sourceAccount = accountRepository.findAccountByAccountNumber(source);
		Optional<Account> recipientAccount = accountRepository.findAccountByAccountNumber(source);
		
		if(sourceAccount.isPresent() && recipientAccount.isPresent()) {
			if(sourceAccount.get().getBalance().compareTo(amount) >= 0) {
				return processtransfer(sourceAccount.get(), recipientAccount.get(), amount);
			}
		}
		
		
		return null;
	}
	
	@Transactional
	private TransferResult processtransfer(Account source, Account AccountReceipient, BigDecimal Amunout) {		
		
		
				
	}


	private TransferResult getFailureOutput(MessageStrings reason, String ... possiblecause) {
		return null;
	}
	
	private TransferResult getSuccessfulOutput(MessageStrings reason, String ... possiblecause) {
		return null;
	}
	
	private TransferResult doTransfer()

}
