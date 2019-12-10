package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer.TransferBuilder;
import com.eduardo.ekki.ekkiTransfer.entity.TransferStatus;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferValidationServiceImpl implements TransferValidationService {
	
	private final TransferRepository transferRepository;
	private final TransferResultProcessService transferResultProcess;
	private final TransferProcessService transferProcess;
	Logger logger = LoggerFactory.getLogger("FILE");
	
	@Autowired
	public TransferValidationServiceImpl(TransferRepository transferRepository,
			TransferResultProcessService transferResultProcess,
			TransferProcessService transferProcess) {
		
		this.transferRepository = transferRepository;
		this.transferResultProcess = transferResultProcess;
		this.transferProcess = transferProcess;
	}	

	@Override
	public TransferResult validateTransferCash(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
		
		TransferResult outputTransfer = validatefaulureCondition(sourceAccount, recipientAccount, amount);
		
		if(outputTransfer != null) {
			return outputTransfer;
		}
		
		TransferBuilder transfer = getNewTransfer(sourceAccount, recipientAccount, amount);
		
		Optional<Transfer> recentTransfer = transferRepository.find(sourceAccount.getAccountNumber(), 
				recipientAccount.getAccountNumber(), amount/*, Timestamp.valueOf(LocalDateTime.now().minus(2, ChronoUnit.MINUTES))*/);
		
		if(recentTransfer.isPresent()) {
			
			transfer.previousTransferID(String.valueOf(recentTransfer.get().getTransferID()));
			
			if(amount.compareTo(new BigDecimal(1000)) > 0) {					
				transfer.status(TransferStatus.PENDING_CONFIRMATION);
				return transferProcess.processTransferAskForConfirmationAndOverrideRecent(transfer.build(), recentTransfer.get());
				
			} else {					
				transfer.status(TransferStatus.COMPLETED);
				return transferProcess.processTransferOverrideRecentTransfer(transfer.build(), recentTransfer.get());
			}
			
		} else {	
			logger.info("recent account not found");
			if(amount.compareTo(new BigDecimal(1000)) > 0) {
				transfer.status(TransferStatus.PENDING_CONFIRMATION);
				return transferProcess.processTransferAskForConfirmation(transfer.build());
			
			} else {					
				transfer.status(TransferStatus.COMPLETED);
				return transferProcess.processTransferHasFunds(transfer.build());
			}
		}
		
	}

	private TransferResult validatefaulureCondition(Account sourceAccount, Account recipientAccount, BigDecimal amount) {
		
		TransferResult answer = null;		
		
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
	
	private TransferBuilder getNewTransfer(Account source, Account recipient, BigDecimal amount) {
		
		TransferBuilder transfer =  
				Transfer.builder()
				.sourceAccount(source.getAccountNumber())
				.recipientAccount(recipient.getAccountNumber())
				.amount(amount)					
				.transferDate(LocalDateTime.now());
		
		if(amount.compareTo(source.getBalance()) > 0) {
			transfer.drawBalance(source.getBalance());			
			transfer.drawCredit(amount.subtract(source.getBalance()));
			
		} else {
			transfer.drawBalance(amount);
			transfer.drawCredit(new BigDecimal(0));
		}
		
		return transfer;
	}
	
}


