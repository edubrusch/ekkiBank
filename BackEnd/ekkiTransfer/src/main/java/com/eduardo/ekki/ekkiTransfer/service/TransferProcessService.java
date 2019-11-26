package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.service.impl.TransferValidationStatus;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public interface TransferProcessService {
	
	TransferResult processTransferAccount(Account accountSource, Account accountReceipient, BigDecimal amount, TransferValidationStatus transferValidation);

}