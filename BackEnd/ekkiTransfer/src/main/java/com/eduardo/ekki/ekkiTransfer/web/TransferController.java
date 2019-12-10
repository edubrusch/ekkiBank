package com.eduardo.ekki.ekkiTransfer.web;

import org.springframework.http.ResponseEntity;

import com.eduardo.ekki.ekkiTransfer.service.request.TransferRequest;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public interface TransferController {	

	ResponseEntity<AccountBalanceResult> accountBalance(long accountID);
	
	ResponseEntity<TransferResult> transferCash(TransferRequest body);
	
	ResponseEntity<TransferResult> confirmTransfer(long transferID);

}
