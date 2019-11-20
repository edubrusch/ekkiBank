package com.eduardo.ekki.ekkiTransfer.web.webImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.ekki.ekkiTransfer.service.impl.AccountBalanceServiceImpl;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;
import com.eduardo.ekki.ekkiTransfer.web.TransferController;

@RestController
public class TransferControllerImpl implements TransferController {
	
	private AccountBalanceServiceImpl accountBalanceService;
	
	@Autowired
	public TransferControllerImpl(AccountBalanceServiceImpl accountBalanceService) {
		this.accountBalanceService = accountBalanceService;
	}

	@Override
	@GetMapping(value = "/balance/{accountID}")
	public ResponseEntity<AccountBalanceResult> accountBalance(String accountID) {
		
		AccountBalanceResult resultAccount = accountBalanceService.accountBalance(accountID);		
		return new ResponseEntity<>(resultAccount, HttpStatus.OK);		
	}

}
