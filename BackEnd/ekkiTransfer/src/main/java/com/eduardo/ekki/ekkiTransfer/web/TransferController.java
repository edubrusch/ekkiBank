package com.eduardo.ekki.ekkiTransfer.web;

import org.springframework.http.ResponseEntity;

import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;

public interface TransferController {

	ResponseEntity<AccountBalanceResult> accountBalance(long accountID);

}
