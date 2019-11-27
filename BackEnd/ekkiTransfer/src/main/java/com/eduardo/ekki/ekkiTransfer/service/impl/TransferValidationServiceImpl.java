package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;

@Service
public class TransferValidationServiceImpl implements TransferValidationService {
	
	@Autowired
	private TransferRepository transferRepository;

	@Override
	public TransferValidationStatus validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
		
		TransferValidationStatus status;
		
		if(sourceAccount.getBalance().compareTo(amount) < 0) {
			
			if(sourceAccount.isHasCredit() && sourceAccount.getCredit().compareTo(amount.subtract(sourceAccount.getBalance())) < 0) {
				status = TransferValidationStatus.NOT_APPROVED_INSUFICIENT_FUNDS;
			} else {
				status = TransferValidationStatus.APPROVED_NEEDS_CREDICART_LOAM;
			}			
			
		} else {
			
			if(transferRepository.find(sourceAccount.getAccountNumber(), recipientAccount.getAccountNumber(), LocalDateTime.now().minus(2, ChronoUnit.MINUTES)).isPresent()) {
				status = TransferValidationStatus.APPROVED_OVERRIDE_RECENT_TRANSACTION;
			} else {
				if(amount.compareTo(new BigDecimal(1000.0)) > 0) {
					status = TransferValidationStatus.APPROVED_NEEDS_PASSWORD;
				} else {
					status = TransferValidationStatus.APPROVED_HAS_FUNDS;
				}
			}
		}		
		
		return status;
		
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

}
