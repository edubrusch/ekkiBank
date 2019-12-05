package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationDTO;
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationDTO.TransferValidationDTOBuilder;

@Service
public class TransferValidationServiceImpl implements TransferValidationService {
	
	private final TransferRepository transferRepository;
	private final TransferResultProcessService transferResultProcess;
	private final TransferProcessService transferProcess;
	
	
	private TransferResult outputTransfer;
	boolean needsConfirmationPassword;
	boolean needsOverridingOldTransfer;
	
	@Autowired
	public TransferValidationServiceImpl(TransferRepository transferRepository,
			TransferResultProcessService transferResultProcess,
			TransferProcessService transferProcess) {
		
		this.transferRepository = transferRepository;
		this.transferResultProcess = transferResultProcess;
		this.transferProcess = transferProcess;
		
		outputTransfer = transferResultProcess.getFailureOutput(MessageStrings.ERROR_TRANSFER_ERROR,					
				MessageStrings.ERROR_TRANSFER_ERROR, "");
	}	

	@Override
	public TransferResult validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
		
		outputTransfer = validatefaulureCondition(sourceAccount, recipientAccount, amount);
		
		if(outputTransfer.isSucess()) {
			outputTransfer = getTransferValidation(sourceAccount, recipientAccount, amount);
		}
		
		
		/*
		 * ############################################
		 * */
		
		
		
		
		
		
		TransferValidationDTOBuilder outputDTO = TransferValidationDTO
				.builder()
				.sourceAccount(sourceAccount)
				.recipientAccount(recipientAccount)
				.amount(amount);
		
		Optional<Transfer> recentTransfer = transferRepository.find(sourceAccount.getAccountNumber(),
				recipientAccount.getAccountNumber(), LocalDateTime.now().minus(2, ChronoUnit.MINUTES), amount);
		
		if(amount.compareTo(new BigDecimal(1000)) > 0) {
			
			recentTransfer.ifPresentOrElse(
					
					foundTransfer -> {				
						outputDTO.previousTransfer(recentTransfer.get());
						
						if(amount.compareTo(new BigDecimal(1000)) > 0) {						
							outputTransfer = transferProcess.processTransferAskForConfirmationAndOverrideRecent(outputDTO.build());
							
						} else {						
							outputTransfer = transferProcess.processTransferOverrideRecentTransfer(outputDTO.build());
						}
						
					} ,
					
					() -> {					
						if(amount.compareTo(new BigDecimal(1000)) > 0) {						
							outputTransfer = transferProcess.processTransferAskForConfirmation(outputDTO.build());
						} else {						
							outputTransfer = transferProcess.processTransferHasFunds(outputDTO.build());
						}				
					}
				);
			
		} else {
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if(sourceAccount.getBalance().compareTo(amount) > 0) {
			
			/*independente de ser maior que 1000 ou nao. se a transf tem recente tem que */
			
			
			
			recentTransfer.ifPresentOrElse(
					
				foundTransfer -> {				
					outputDTO.previousTransfer(recentTransfer.get());
					
					if(amount.compareTo(new BigDecimal(1000)) > 0) {						
						outputTransfer = transferProcess.processTransferAskForConfirmationAndOverrideRecent(outputDTO.build());
						
					} else {						
						outputTransfer = transferProcess.processTransferOverrideRecentTransfer(outputDTO.build());
					}
					
				} ,
				
				() -> {					
					if(amount.compareTo(new BigDecimal(1000)) > 0) {						
						outputTransfer = transferProcess.processTransferAskForConfirmation(outputDTO.build());
					} else {						
						outputTransfer = transferProcess.processTransferHasFunds(outputDTO.build());
					}				
				}
			);
		}		
		
		return outputTransfer;		
	}

	private TransferResult getTransferValidation(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
		
	
		
		TransferValidationDTOBuilder outputDTO = TransferValidationDTO
				.builder()
				.sourceAccount(sourceAccount)
				.recipientAccount(recipientAccount)
				.amount(amount);
		
		Optional<Transfer> recentTransfer = transferRepository.find(sourceAccount.getAccountNumber(),
				recipientAccount.getAccountNumber(), LocalDateTime.now().minus(2, ChronoUnit.MINUTES), amount);		
		
		recentTransfer.ifPresentOrElse(
				
				foundTransfer -> {
					outputDTO.previousTransfer(recentTransfer.get());
					needsOverridingOldTransfer = true; } ,
								
				() -> { needsOverridingOldTransfer = false; }
				);	
		
		if(amount.compareTo(new BigDecimal(1000)) > 0) {
			needsConfirmationPassword = true;
		} else {
			needsConfirmationPassword = false;
		}
		
		if(amount.compareTo(sourceAccount.getBalance()) > 0) {
			outputDTO.drawBalance(sourceAccount.getBalance());			
			outputDTO.drawCredit(amount.subtract(sourceAccount.getBalance()));
			
		} else {
			outputDTO.drawBalance(amount);
			outputDTO.drawCredit(new BigDecimal(0));
		}
		
		
		//outputTransfer = transferProcess.processTransferUseCredit(outputDTO.build());
		//make generic method with draw according to DTO
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return null;
	}

	private TransferResult validatefaulureCondition(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
		
		TransferResult answer = transferResultProcess.getSuccessfulOutput(null, null);		
		
			if(sourceAccount.getBalance().compareTo(amount) <= 0) {
			
			if(!sourceAccount.isHasCredit()) {
				answer = transferResultProcess.getFailureOutput(MessageStrings.ERROR_TRANSFER_NOT_POSSIBLE,					
						MessageStrings.ERROR_NOT_APPROVED_NEEDS_CREDITCARD, recipientAccount.getAccountNumber());
			} else {				
				if(sourceAccount.getBalance().add(sourceAccount.getCredit()).compareTo(amount) < 0) {
					answer = transferResultProcess.getFailureOutput(MessageStrings.ERROR_TRANSFER_NOT_POSSIBLE,					
							MessageStrings.ERROR_NOT_APPROVED_NO_FUNDS_CREDIT, recipientAccount.getAccountNumber());
				}						
			}			
		}
			return answer;
	}	
	
	
	
	/*
	 * does not have enough funds
	 * has enough credit
	 * 
	  if(sourceAccount.getBalance().add(sourceAccount.getCredit()).compareTo(amount) >= 0) {
					BigDecimal draw = sourceAccount.getBalance();
										
					outputDTO.drawBalance(draw);
					outputDTO.drawCredit(amount.subtract(draw));
					outputTransfer = transferProcess.processTransferUseCredit(outputDTO.build());
				
				} else {
					
				}		
	 * 
	 * 
	 * */
	
	/**
	 * 
	 * Caso o valor da transferência seja maior que o saldo atual da conta, sinalizar o
	 * usuário que irá ser utilizado o cartão de crédito para completar a transação, caso
	 * não tenha cartão cadastrado, dar a opção de cadastro de um novo.
	 * Transferências acima de $1000, usuário precisa colocar a senha.
	 * Se for transferido em menos de 2 minutos, o mesmo valor, para o mesmo usuário,
	 * cancelar a transação anterior e manter a última.		
	 * 
	 * 
	 * 
	 */	

}


