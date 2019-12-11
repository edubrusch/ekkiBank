package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStringsEnum;
import com.eduardo.ekki.ekkiTransfer.common.TransferResultProcess;
import com.eduardo.ekki.ekkiTransfer.common.TransferStatusEnum;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer.TransferBuilder;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferValidationServiceImpl implements TransferValidationService {
	
	private final TransferRepository transferRepository;
	private TransferResultProcess transferResultProcess;
	private final TransferProcessService transferProcess;
	Logger logger = LoggerFactory.getLogger("FILE");
	
	@Autowired
	public TransferValidationServiceImpl(TransferRepository transferRepository, TransferProcessService transferProcess) {
		
		this.transferRepository = transferRepository;
		this.transferResultProcess = new TransferResultProcess();
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
				recipientAccount.getAccountNumber(), amount);
		
		if(recentTransfer.isPresent()) {
			
			transfer.previousTransferID(String.valueOf(recentTransfer.get().getTransferID()));
			
			if(amount.compareTo(new BigDecimal(1000)) > 0) {					
				transfer.status(TransferStatusEnum.PENDING_CONFIRMATION);
				return transferProcess.processTransferAskForConfirmationAndOverrideRecent(transfer.build(), recentTransfer.get());
				
			} else {					
				transfer.status(TransferStatusEnum.COMPLETED);
				return transferProcess.processTransferOverrideRecentTransfer(transfer.build(), recentTransfer.get());
			}
			
		} else {
			if(amount.compareTo(new BigDecimal(1000)) > 0) {
				transfer.status(TransferStatusEnum.PENDING_CONFIRMATION);
				return transferProcess.processTransferAskForConfirmation(transfer.build());
			
			} else {					
				transfer.status(TransferStatusEnum.COMPLETED);
				return transferProcess.processTransferHasFunds(transfer.build());
			}
		}
		
	}

	private TransferResult validatefaulureCondition(Account sourceAccount, Account recipientAccount, BigDecimal amount) {		
		
			if(sourceAccount.getBalance().compareTo(amount) <= 0) {
			
			if(sourceAccount.isHasCredit()) {
				if((sourceAccount.getBalance().add(sourceAccount.getCredit()).compareTo(amount) < 0)) {
					return transferResultProcess.getFailureOutput(MessageStringsEnum.ERROR_TRANSFER_NOT_POSSIBLE,					
							MessageStringsEnum.ERROR_NOT_APPROVED_NO_FUNDS_CREDIT, sourceAccount.getAccountNumber());
				} else {
					return null;
				}
			} else {
				return transferResultProcess.getFailureOutput(MessageStringsEnum.ERROR_TRANSFER_NOT_POSSIBLE,					
						MessageStringsEnum.ERROR_NOT_APPROVED_NEEDS_CREDITCARD, sourceAccount.getAccountNumber());
			} 
		} else {
			return null;
		}			 
	}
	
	private TransferBuilder getNewTransfer(Account source, Account recipient, BigDecimal amount) {
		
		TransferBuilder transfer =  
				Transfer.builder()
				.sourceAccount(source)
				.recipientAccount(recipient)
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

	@Override
	public TransferResult validateTransferConfirm(Transfer transfer) {
		
		if(transfer.getStatus().equals(TransferStatusEnum.PENDING_CONFIRMATION))
			return  transferProcess.processTransferConfirmation(transfer);	
		
		return transferResultProcess.getFailureOutput(MessageStringsEnum.ERROR_TRANSFER_NOT_COMPLETED, MessageStringsEnum.ERROR_TRANSFER_NOT_FOUND, 0);
	}
	
}
