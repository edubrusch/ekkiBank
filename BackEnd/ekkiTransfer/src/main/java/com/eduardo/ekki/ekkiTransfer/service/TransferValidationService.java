package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.service.impl.TransferValidationStatus;

@Service
public interface TransferValidationService {
	
	TransferValidationStatus validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount);

}
