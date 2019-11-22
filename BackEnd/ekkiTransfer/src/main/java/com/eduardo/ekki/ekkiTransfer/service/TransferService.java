package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public interface TransferService {
	
	public TransferResult transferCash(long sourceAccountNumber, long recipientAccountNumber, BigDecimal amount);

}
