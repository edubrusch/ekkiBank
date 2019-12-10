package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStringsEnum;
import com.eduardo.ekki.ekkiTransfer.common.TransferResultProcess;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferService;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferServiceImpl implements TransferService{
	
	private final AccountRepository accountRepository;	
	private final TransferValidationService transferValidation;	
	private final TransferResultProcess transferResultProcess;	
	
	@Autowired
	public TransferServiceImpl(AccountRepository accountRepository,
			TransferValidationService transferValidation) {
				
		this.accountRepository = accountRepository;
		this.transferValidation = transferValidation;		
		this.transferResultProcess = new TransferResultProcess();	
	}	

	@Override
	public TransferResult transferCash(long sourceAccountNumber, long recipientAccountNumber, BigDecimal amount) {		
		
		Optional<Account> sourceAccount = accountRepository.findAccountByAccountNumber(sourceAccountNumber);
		Optional<Account> recipientAccount = accountRepository.findAccountByAccountNumber(recipientAccountNumber);
		
		if(sourceAccount.isPresent() && recipientAccount.isPresent()) {
			return transferValidation.validateTransferCash(sourceAccount.get(), recipientAccount.get(), amount);
		} else {
			if(sourceAccount.isEmpty()) {
				return transferResultProcess.getFailureOutput(MessageStringsEnum.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT,					
						MessageStringsEnum.ERROR_TRANSFER_RECIPIENT_ACCOUNT_NOT_FOUND, recipientAccountNumber);
			} else {
				return transferResultProcess.getFailureOutput(MessageStringsEnum.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT,
						MessageStringsEnum.ERROR_TRANSFER_SOURCE_ACCOUNT_NOT_FOUND, sourceAccountNumber);
			}
		}
		
	}

	@Override
	public TransferResult confirmTransfer(long transactionID) {
		
		return null;
	}

}
