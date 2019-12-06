package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

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
	
	
	private final AccountRepository accountRepository;	
	
	private TransferRepository transferRepository;		
	
	private TransferResultProcessService transferResultProcess;
	
	
	@Autowired
	public TransferProcessServiceImpl(AccountRepository accountRepository, TransferRepository transferRepository, 
			TransferResultProcessService transferResultProcess) {
		
		this.accountRepository = accountRepository;
		this.transferRepository = transferRepository;
		this.transferResultProcess = transferResultProcess;
	}
	
	
	@Override
	@Transactional
	public TransferResult processTransferHasFunds(Transfer transfer) {		
		
		transferAccount(transfer);		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}

	@Override
	@Transactional
	public TransferResult processTransferOverrideRecentTransfer(Transfer transfer, Transfer previousTransfer) {
		
		previousTransfer.setStatus(TransferStatus.CANCELED_OVERRIDEN);		
		transferRepository.save(previousTransfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.APPROVED_OVERRIDE_RECENT_TRANSACTION, transfer);
	}
	

	@Override
	@Transactional
	public TransferResult processTransferAskForConfirmation(Transfer transfer) {
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.APPROVED_NOT_COMPLETED, transfer);
	}


	@Override
	@Transactional
	public TransferResult processTransferAskForConfirmationAndOverrideRecent(Transfer transfer, Transfer previousTransfer) {			
		
		previousTransfer.setStatus(TransferStatus.CANCELED_OVERRIDEN);		
		transferRepository.save(previousTransfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.APPROVED_NOT_COMPLETED, transfer);		
	}
	
	
	@Override
	@Transactional
	public TransferResult processTransferConfirmation(String transferID) {
		
		Optional<Transfer> transfer = transferRepository.findByID(transferID);
		
		if(transfer.isPresent()) {		
			transfer.get().setStatus(TransferStatus.COMPLETED);
			transferRepository.save(transfer.get());
			return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_CONFIRM_TRANSFER, transfer.get());
			
		}else {			
			return transferResultProcess.getFailureOutput(MessageStrings.ERROR_TRANSFER_NOT_COMPLETED, MessageStrings.ERROR_TRANSFER_NOT_FOUND, "");
		}
		
	}	

	private void transferAccount(Transfer transferData) {
		
		Optional<Account> source = accountRepository.findById(transferData.getSourceAccount());
		Optional<Account> recipient = accountRepository.findById(transferData.getRecipientAccount());			
		
		BigDecimal balanceFinalSource = source.get().getBalance().subtract(transferData.getDrawBalance());
		BigDecimal balanceFinalCreditSource = source.get().getCredit().subtract(transferData.getDrawCredit());		
		BigDecimal balanceFinalReceipient = recipient.get().getBalance().add(transferData.getAmount());
		
		source.get().setBalance(balanceFinalSource);
		source.get().setCredit(balanceFinalCreditSource);
		recipient.get().setBalance(balanceFinalReceipient);
		accountRepository.save(source.get());
		accountRepository.save(recipient.get());
	}
	
}
