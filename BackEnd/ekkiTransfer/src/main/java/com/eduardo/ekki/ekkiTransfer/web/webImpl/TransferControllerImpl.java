package com.eduardo.ekki.ekkiTransfer.web.webImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.ekki.ekkiTransfer.service.impl.AccountBalanceServiceImpl;
import com.eduardo.ekki.ekkiTransfer.service.impl.TransferServiceImpl;
import com.eduardo.ekki.ekkiTransfer.service.request.TransferRequest;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;
import com.eduardo.ekki.ekkiTransfer.web.TransferController;

@RestController
public class TransferControllerImpl implements TransferController {
	
	@Autowired
	private AccountBalanceServiceImpl accountBalanceService;
	
	@Autowired
	private TransferServiceImpl transferService;

	
	@Override
	@GetMapping(value = "/balance/{id}")
	public ResponseEntity<AccountBalanceResult> accountBalance(@PathVariable("id") String accountID) {
		
		AccountBalanceResult resultAccount = accountBalanceService.accountBalance(accountID);		
		return new ResponseEntity<>(resultAccount, HttpStatus.OK);		
	}

	@Override
	@PostMapping(value = "/transfer", produces = "application/json", consumes = "application/json")
	public ResponseEntity<TransferResult> transferCash(@RequestBody TransferRequest body) {
		
		TransferResult resultTransfer = transferService.transferCash(body.getSourceAccount(), body.getRecipientAccount(), body.getAmount());
		
		return new ResponseEntity<>(resultTransfer, HttpStatus.OK);
	}

}
