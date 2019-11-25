package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.service.impl.TransferValidationStatus;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public interface TransferProcessService {
	
	TransferValidationStatus validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount);
	
	TransferResult processTransferAccount(Account accountSource, Account accountReceipient, BigDecimal amount, TransferValidationStatus transferValidation);

}
