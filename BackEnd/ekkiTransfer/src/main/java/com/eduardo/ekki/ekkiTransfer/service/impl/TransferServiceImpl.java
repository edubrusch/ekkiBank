package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferService;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferServiceImpl implements TransferService{
	private TransferResult output;
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransferValidationService transferValidation;
	@Autowired
	private TransferProcessService transferProcess;
	@Autowired
	private TransferResultProcessService transferResult;

	@Override
	public TransferResult transferCash(String sourceAccountNumber, String recipientAccountNumber, BigDecimal amount) {
		
		Optional<Account> sourceAccount = accountRepository.findAccountByAccountNumber(sourceAccountNumber);
		Optional<Account> recipientAccount = accountRepository.findAccountByAccountNumber(recipientAccountNumber);
		
		sourceAccount.ifPresentOrElse(SourceAccountFound -> {
			
			recipientAccount.ifPresentOrElse(recipientAccountFound -> {
				
				TransferValidationStatus isTransferOK = transferValidation.validateTransferCash(sourceAccount.get(), recipientAccount.get(), amount);				
				output = transferProcess.processTransferAccount(sourceAccount.get(), recipientAccount.get(), amount, isTransferOK);
				
			}, () -> {output = transferResult.getFailureOutput(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT,					
					MessageStrings.ERROR_TRANSFER_RECIPIENT_ACCOUNT_NOT_FOUND, recipientAccountNumber);});
			
		}, () -> {output = transferResult.getFailureOutput(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT,
				MessageStrings.ERROR_TRANSFER_SOURCE_ACCOUNT_NOT_FOUND, sourceAccountNumber);});
		
		return output;
	}

}
