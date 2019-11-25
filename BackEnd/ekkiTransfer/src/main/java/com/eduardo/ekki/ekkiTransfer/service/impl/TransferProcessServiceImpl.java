package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;


public class TransferProcessServiceImpl implements TransferProcessService{
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransferRepository transferRepository;
	@Autowired
	private TransferResultProcessService transferResultProcess;

	@Override
	public TransferValidationStatus validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
		
		
		
		return null;
		
		/**
		 * 
		 * Caso o valor da transferência seja maior que o saldo atual da conta, sinalizar o
		 * usuário que irá ser utilizado o cartão de crédito para completar a transação, caso
		 * não tenha cartão cadastrado, dar a opção de cadastro de um novo.
		 * Transferências acima de $1000, usuário precisa colocar a senha.
		 * Se for transferido em menos de 2 minutos, o mesmo valor, para o mesmo usuário,
		 * cancelar a transação anterior e manter a última.
		 * 
		 */
		
	}	

	@Override
	
	public TransferResult processTransferAccount(Account accountSource, Account accountReceipient, BigDecimal amount, TransferValidationStatus transferValidation) {
		
		switch(transferValidation) {
		
		case APPROVED_HAS_FUNDS:
			break;
		case APPROVED_NEEDS_CREDICART_LOAM:
			break;
		case APPROVED_NEEDS_PASSWORD:
			break;
		case APPROVED_OVERRIDE_RECENT_TRANSACTION:
			break;
		case NOT_APPROVED_INSUFICIENT_FUNDS:
			break;
		default:
			break;
		
		}
		return null;
		
	}
	
	@Transactional
	private TransferResult execucuteRealTransfer(Account accountSource, Account accountReceipient, BigDecimal amount) {
		BigDecimal amountFinalSource = accountSource.getBalance().subtract(amount);
		BigDecimal amountFinalReceipient = accountReceipient.getBalance().add(amount);
		
		accountSource.setBalance(amountFinalSource);
		accountReceipient.setBalance(amountFinalReceipient);
		
		Transfer transfer = Transfer
		.builder()
		.amount(amount)
		.sourceAccount(accountSource.getAccountNumber())
		.destinationAccount(accountReceipient.getAccountNumber())
		.amount(amount)
		.transferDate(new Date())
		.build();
				
		accountRepository.save(accountSource);
		accountRepository.save(accountSource);		
		transferRepository.save(transfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}
	

}
