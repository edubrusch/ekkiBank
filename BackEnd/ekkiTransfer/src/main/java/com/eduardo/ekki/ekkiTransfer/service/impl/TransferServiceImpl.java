package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferService;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferServiceImpl implements TransferService{
	
	private final AccountRepository accountRepository;	
	private final TransferValidationService transferValidation;	
	private final TransferResultProcessService transferResultProcess;
	private TransferResult output;
	
	@Autowired
	public TransferServiceImpl(AccountRepository accountRepository,
			TransferValidationService transferValidation,
			TransferResultProcessService transferResult) {
		
		
		this.accountRepository = accountRepository;
		this.transferValidation = transferValidation;		
		this.transferResultProcess = transferResult;
		output = transferResultProcess.getFailureOutput(
				MessageStrings.ERROR_TRANSFER_ERROR, MessageStrings.ERROR_TRANSFER_ERROR, "");
	}	

	@Override
	public TransferResult transferCash(String sourceAccountNumber, String recipientAccountNumber, BigDecimal amount) {
		
		
		Optional<Account> sourceAccount = accountRepository.findAccountByAccountNumber(sourceAccountNumber);
		Optional<Account> recipientAccount = accountRepository.findAccountByAccountNumber(recipientAccountNumber);
		
		sourceAccount.ifPresentOrElse(SourceAccountFound -> {
			
			recipientAccount.ifPresentOrElse(recipientAccountFound -> {
				
				output =  transferValidation.validateTransferCash(sourceAccount.get(), recipientAccount.get(), amount);				
				
			}, () -> {output = transferResultProcess.getFailureOutput(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT,					
					MessageStrings.ERROR_TRANSFER_RECIPIENT_ACCOUNT_NOT_FOUND, recipientAccountNumber);});
			
		}, () -> {output = transferResultProcess.getFailureOutput(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT,
				MessageStrings.ERROR_TRANSFER_SOURCE_ACCOUNT_NOT_FOUND, sourceAccountNumber);});
				
		return output;
	}
	
	

}
