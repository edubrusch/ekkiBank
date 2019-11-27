package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.entity.TransferStatus;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferProcessServiceImpl implements TransferProcessService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransferRepository transferRepository;
		
	@Autowired
	private TransferResultProcessService transferResultProcess;
	
	@Autowired
	private TransferResultProcessServiceImpl processTransfer;

	

	@Override
	public TransferResult processTransferAccount(Account accountSource, Account accountReceipient, BigDecimal amount, TransferValidationStatus transferValidation) {
		
		switch(transferValidation) {
		
		case APPROVED_HAS_FUNDS:
			return execucuteTransferHasFunds(accountSource, accountReceipient, amount);
		case APPROVED_NEEDS_CREDICART_LOAM:
			return executeTransferCreditCardLoam(accountSource, accountReceipient, amount);
		case APPROVED_NEEDS_PASSWORD:
			return createTransferRequirePassword(accountSource, accountReceipient, amount);
		case APPROVED_OVERRIDE_RECENT_TRANSACTION:
			return overrideTransfer(accountSource, accountReceipient, amount);
		case NOT_APPROVED_INSUFICIENT_FUNDS:
			return processTransfer.getFailureOutput(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT, MessageStrings.SUCCESS_ACCOUNT_FOUND, accountSource.getAccountNumber());
		default:
			return processTransfer.getFailureOutput(MessageStrings.ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT, MessageStrings.SUCCESS_ACCOUNT_FOUND, accountSource.getAccountNumber());
		}
	}
	
	private TransferResult execucuteTransferHasFunds(Account accountSource, Account accountReceipient, BigDecimal amount) {
		BigDecimal amountFinalSource = accountSource.getBalance().subtract(amount);
		BigDecimal amountFinalReceipient = accountReceipient.getBalance().add(amount);
		
		accountSource.setBalance(amountFinalSource);
		accountReceipient.setBalance(amountFinalReceipient);
		
		Transfer transfer = Transfer
		.builder()
		.sourceAccount(accountSource.getAccountNumber())
		.destinationAccount(accountReceipient.getAccountNumber())
		.amount(amount)
		.status(TransferStatus.COMPLETED)
		.transferDate(LocalDateTime.now())
		.build();
		
		accountRepository.save(accountSource);
		accountRepository.save(accountSource);
		transferRepository.save(transfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}

	private TransferResult overrideTransfer(Account accountSource, Account accountReceipient, BigDecimal amount) {
		BigDecimal amountFinalSource = accountSource.getBalance().subtract(amount);
		BigDecimal amountFinalReceipient = accountReceipient.getBalance().add(amount);
		
		accountSource.setBalance(amountFinalSource);
		accountReceipient.setBalance(amountFinalReceipient);
		
		Transfer transfer = Transfer
		.builder()
		.sourceAccount(accountSource.getAccountNumber())
		.destinationAccount(accountReceipient.getAccountNumber())
		.amount(amount)
		.status(TransferStatus.COMPLETED)
		.transferDate(LocalDateTime.now())
		.build();
		
		accountRepository.save(accountSource);
		accountRepository.save(accountSource);
		transferRepository.save(transfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}

	private TransferResult createTransferRequirePassword(Account accountSource, Account accountReceipient, BigDecimal amount) {
		
		Transfer transfer = Transfer
		.builder()		
		.sourceAccount(accountSource.getAccountNumber())
		.destinationAccount(accountReceipient.getAccountNumber())
		.amount(amount)
		.status(TransferStatus.PENDING_CONFIRMATION)
		.transferDate(LocalDateTime.now())
		.build();				

		transferRepository.saveAndFlush(transfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}

	private TransferResult executeTransferCreditCardLoam(Account accountSource, Account accountReceipient, BigDecimal amount) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

	@Transactional
	private TransferResult execucuteStraightTransfer(Account accountSource, Account accountReceipient, BigDecimal amount) {
		BigDecimal amountFinalSource = accountSource.getBalance().subtract(amount);
		BigDecimal amountFinalReceipient = accountReceipient.getBalance().add(amount);
		
		accountSource.setBalance(amountFinalSource);
		accountReceipient.setBalance(amountFinalReceipient);
		
		Transfer transfer = Transfer
		.builder()		
		.sourceAccount(accountSource.getAccountNumber())
		.destinationAccount(accountReceipient.getAccountNumber())
		.amount(amount)
		.status(TransferStatus.COMPLETED)
		.transferDate(LocalDateTime.now())
		.build();
				
		accountRepository.save(accountSource);
		accountRepository.save(accountSource);
		transferRepository.save(transfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}
	

}
