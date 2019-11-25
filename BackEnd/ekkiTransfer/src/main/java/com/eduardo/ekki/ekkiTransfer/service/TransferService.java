package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public interface TransferService {
	
	public TransferResult transferCash(String sourceAccountNumber, String recipientAccountNumber, BigDecimal amount);	

}
