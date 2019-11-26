package com.eduardo.ekki.ekkiTransfer.service;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;

@Service
public interface AccountBalanceService {
	
	AccountBalanceResult accountBalance(String accountID);

}
