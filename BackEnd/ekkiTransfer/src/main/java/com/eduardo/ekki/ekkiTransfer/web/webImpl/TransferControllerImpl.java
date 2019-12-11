package com.eduardo.ekki.ekkiTransfer.web.webImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.ekki.ekkiTransfer.service.AccountBalanceService;
import com.eduardo.ekki.ekkiTransfer.service.TransferService;
import com.eduardo.ekki.ekkiTransfer.service.impl.AccountBalanceServiceImpl;
import com.eduardo.ekki.ekkiTransfer.service.impl.TransferServiceImpl;
import com.eduardo.ekki.ekkiTransfer.service.request.TransferRequest;
import com.eduardo.ekki.ekkiTransfer.service.result.AccountBalanceResult;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;
import com.eduardo.ekki.ekkiTransfer.web.TransferController;

@RestController
public class TransferControllerImpl implements TransferController {
	
	
	private final AccountBalanceService accountBalanceService;
	private final TransferService transferService;
	
	@Autowired
	public TransferControllerImpl(AccountBalanceServiceImpl accountBalanceService, TransferServiceImpl transferService) {
		this.accountBalanceService = accountBalanceService;
		this.transferService = transferService;
	}
	
	@Override
	@GetMapping(value = "/balance/{id}")
	public ResponseEntity<AccountBalanceResult> accountBalance(@PathVariable("id") long accountID) {
		
		AccountBalanceResult resultAccount = accountBalanceService.accountBalance(accountID);		
		return new ResponseEntity<>(resultAccount, HttpStatus.OK);		
	}

	@Override
	@PostMapping(value = "/transfer", produces = "application/json", consumes = "application/json")
	public ResponseEntity<TransferResult> transferCash(@RequestBody TransferRequest body) {
		
 		TransferResult resultTransfer = transferService.transferCash(body.getSourceAccount(), body.getRecipientAccount(), body.getAmount());
		
		return new ResponseEntity<>(resultTransfer, HttpStatus.OK);
	}

	@Override
	@PutMapping(value = "/transfer/{transferID}")
	public ResponseEntity<TransferResult> confirmTransfer(@PathVariable("transferID") long transferID) {
		TransferResult resultTransfer = transferService.confirmTransfer(transferID);
		
		return new ResponseEntity<>(resultTransfer, HttpStatus.OK);
	}

}
