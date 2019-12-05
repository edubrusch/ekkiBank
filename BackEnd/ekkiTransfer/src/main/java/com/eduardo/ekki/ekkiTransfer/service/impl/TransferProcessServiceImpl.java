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
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationDTO;

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
	public TransferResult processTransferHasFunds(TransferValidationDTO transferDTO) {
		transferAccount(
				transferDTO.getSourceAccount(), 
				transferDTO.getRecipientAccount(), 
				transferDTO.getAmount(),  
				new BigDecimal(0), 
				transferDTO.getAmount());
		
		Transfer transfer = createTransfer(
				transferDTO.getSourceAccount(),
				transferDTO.getRecipientAccount(),
				transferDTO.getAmount(),
				TransferStatus.COMPLETED);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}
	
	@Override
	@Transactional
	public TransferResult processTransferOverrideRecentTransfer(TransferValidationDTO transferDTO) {
		
		transferDTO.getPreviousTransfer().setStatus(TransferStatus.CANCELED_OVERRIDEN);		
		transferRepository.save(transferDTO.getPreviousTransfer());		
		
		Transfer transfer = createTransfer(
				transferDTO.getSourceAccount(), 
				transferDTO.getRecipientAccount(), 
				transferDTO.getAmount(), 
				TransferStatus.COMPLETED);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.APPROVED_OVERRIDE_RECENT_TRANSACTION, transfer);
	}


	@Override
	@Transactional
	public TransferResult processTransferAskForConfirmation(TransferValidationDTO transferDTO) {
		
		Transfer transfer = createTransfer(
				transferDTO.getSourceAccount(), 
				transferDTO.getRecipientAccount(), 
				transferDTO.getAmount(), 
				TransferStatus.PENDING_CONFIRMATION);
		
		return transferResultProcess.getSuccessfulOutput(MessageStrings.APPROVED_OVERRIDE_RECENT_TRANSACTION, transfer);
	}


	@Override
	@Transactional
	public TransferResult processTransferAskForConfirmationAndOverrideRecent(TransferValidationDTO transferDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional
	public TransferResult processTransferUseCredit(TransferValidationDTO transferDTO) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	

	private void transferAccount(
			Account accountSource,
			Account accountReceipient, 
			BigDecimal drawFromAccount, 
			BigDecimal drawFromCredit, 
			BigDecimal creditRecivingAccount) {
		
		BigDecimal balanceFinalSource = accountSource.getBalance().subtract(drawFromAccount);
		BigDecimal balanceFinalCreditSource = accountSource.getCredit().subtract(drawFromCredit);
		BigDecimal balanceFinalReceipient = accountReceipient.getBalance().add(creditRecivingAccount);
		
		accountSource.setBalance(balanceFinalSource);
		accountSource.setCredit(balanceFinalCreditSource);
		accountReceipient.setBalance(balanceFinalReceipient);
		accountRepository.save(accountSource);
		accountRepository.save(accountReceipient);
	}
	
	
	private Transfer createTransfer(Account source, Account recipient, BigDecimal amount, TransferStatus status) {
		Transfer transfer = Transfer
				.builder()
				.sourceAccount(source.getAccountNumber())
				.destinationAccount(recipient.getAccountNumber())
				.amount(amount)
				.status(status)
				.transferDate(LocalDateTime.now())
				.build();				
				
				transferRepository.save(transfer);
				
				return transfer;		
	}

	

}
