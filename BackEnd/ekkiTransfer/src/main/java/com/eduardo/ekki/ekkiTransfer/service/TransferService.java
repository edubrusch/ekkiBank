package com.eduardo.ekki.ekkiTransfer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public interface TransferService {
	
	public TransferResult transferCash(String sourceAccountNumber, String recipientAccountNumber, BigDecimal amount);	

}
