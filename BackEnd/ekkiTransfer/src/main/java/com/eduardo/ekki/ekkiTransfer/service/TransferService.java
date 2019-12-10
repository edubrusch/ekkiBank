package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public interface TransferService {
	
	TransferResult transferCash(long sourceAccountNumber, long recipientAccountNumber, BigDecimal amount);	
	
	TransferResult confirmTransfer(long transactionID);

}
