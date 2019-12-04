package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationDTO;
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationDTO.TransferValidationDTOBuilder;
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationStatus;

@Service
public class TransferValidationServiceImpl implements TransferValidationService {
	
	@Autowired
	private TransferRepository transferRepository;

	@Override
	public TransferValidationDTO validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
				
		TransferValidationDTOBuilder output = TransferValidationDTO
				.builder()
				.sourceAccount(sourceAccount)
				.recipientAccount(recipientAccount)
				.amount(amount);		
		
		if(sourceAccount.getBalance().compareTo(amount) < 0) {
			
			if(!sourceAccount.isHasCredit()) {				
				output.status(TransferValidationStatus.NOT_APPROVED_NEEDS_CREDITCARD);
				
			} else {
				
				if(sourceAccount.getBalance().add(sourceAccount.getCredit()).compareTo(amount) > 0) {
					BigDecimal draw = sourceAccount.getBalance();
					
					output.status(TransferValidationStatus.APPROVED_NEEDS_CREDICART_LOAM);
					output.drawBalance(draw);
					output.drawCredit(amount.subtract(draw));
				
				} else {
					output.status(TransferValidationStatus.NOT_APPROVED_INSUFICIENT_FUNDS);
				}
				
			}			
			
		} else {
			
			/*independente de ser maior que 1000 ou nao. se a transf tem recente tem que */
			
			Optional<Transfer> recentTransfer = transferRepository.find(sourceAccount.getAccountNumber(),
					recipientAccount.getAccountNumber(), LocalDateTime.now().minus(2, ChronoUnit.MINUTES));			
			
			recentTransfer.ifPresentOrElse(
					
				foundTransfer -> {
				
					output.previousTransfer(recentTransfer.get());
					
					if(amount.compareTo(new BigDecimal(1000)) > 0) {
						output.status(TransferValidationStatus.APPROVED_OVERRIDE_NEEDS_PASSWORD);
					} else {
						output.status(TransferValidationStatus.APPROVED_OVERRIDE_RECENT_TRANSACTION);
					}
					
				} ,
				
				() -> {				
					
					if(amount.compareTo(new BigDecimal(1000)) > 0) {
						output.status(TransferValidationStatus.APPROVED_NEEDS_PASSWORD);
					} else {
						output.status(TransferValidationStatus.APPROVED_HAS_FUNDS);
					}				
				}
			);
			
		}		
		
		return output.build();
		
		/**
		 * 
		 * Caso o valor da transferência seja maior que o saldo atual da conta, sinalizar o
		 * usuário que irá ser utilizado o cartão de crédito para completar a transação, caso
		 * não tenha cartão cadastrado, dar a opção de cadastro de um novo.
		 * Transferências acima de $1000, usuário precisa colocar a senha.
		 * Se for transferido em menos de 2 minutos, o mesmo valor, para o mesmo usuário,
		 * cancelar a transação anterior e manter a última.		
		 */
		
	}	
	
	
	
	

}
