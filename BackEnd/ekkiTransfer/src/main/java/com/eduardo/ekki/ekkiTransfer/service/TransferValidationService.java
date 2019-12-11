package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public interface TransferValidationService {
	
	TransferResult validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount);
	
	TransferResult validateTransferConfirm(Transfer transfer);

}
