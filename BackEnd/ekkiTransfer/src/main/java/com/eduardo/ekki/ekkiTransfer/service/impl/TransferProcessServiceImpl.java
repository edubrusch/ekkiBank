package com.eduardo.ekki.ekkiTransfer.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.ekki.ekkiTransfer.common.MessageStringsEnum;
import com.eduardo.ekki.ekkiTransfer.common.TransferResultProcess;
import com.eduardo.ekki.ekkiTransfer.common.TransferStatusEnum;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.repository.AccountRepository;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
@Transactional
public class TransferProcessServiceImpl implements TransferProcessService{
		
	private final AccountRepository accountRepository;		
	private TransferRepository transferRepository;			
	private TransferResultProcess transferResultProcess;	
	
	@Autowired
	public TransferProcessServiceImpl(AccountRepository accountRepository, TransferRepository transferRepository) {
		
		this.accountRepository = accountRepository;
		this.transferRepository = transferRepository;
		this.transferResultProcess = new TransferResultProcess();
	}	
	
	@Override	
	public TransferResult processTransferHasFunds(Transfer transfer) {		
		
		transferAccount(transfer);		
		if(transfer.getDrawCredit().compareTo(new BigDecimal(0)) > 0) {
			return transferResultProcess.getSuccessfulOutput(MessageStringsEnum.SUCCESS_APPROVED_USED_CREDICART_LOAM, transfer);
		}
		return transferResultProcess.getSuccessfulOutput(MessageStringsEnum.SUCCESS_TRANSFER_ACCOUNT, transfer);
	}

	@Override	
	public TransferResult processTransferOverrideRecentTransfer(Transfer transfer, Transfer previousTransfer) {
		
		previousTransfer.setStatus(TransferStatusEnum.CANCELED_OVERRIDEN);		
		transferRepository.save(previousTransfer);
		transferRepository.save(transfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStringsEnum.APPROVED_OVERRIDE_RECENT_TRANSACTION, transfer);
	}
	

	@Override	
	public TransferResult processTransferAskForConfirmation(Transfer transfer) {
		transferRepository.save(transfer);
		return transferResultProcess.getSuccessfulOutput(MessageStringsEnum.APPROVED_NOT_COMPLETED, transfer);
	}

	@Override	
	public TransferResult processTransferAskForConfirmationAndOverrideRecent(Transfer transfer, Transfer previousTransfer) {
		
		previousTransfer.setStatus(TransferStatusEnum.CANCELED_OVERRIDEN);		
		transferRepository.save(previousTransfer);
		transferRepository.save(transfer);
		
		return transferResultProcess.getSuccessfulOutput(MessageStringsEnum.APPROVED_NOT_COMPLETED, transfer);
	}	
	
	@Override	
	public TransferResult processTransferConfirmation(Transfer transfer) {
		transferAccount(transfer);		
		transfer.setStatus(TransferStatusEnum.COMPLETED);
		transferRepository.save(transfer);
		return transferResultProcess.getSuccessfulOutput(MessageStringsEnum.SUCCESS_CONFIRM_TRANSFER, transfer);
	}

	private void transferAccount(Transfer transferData) {
		
		Optional<Account> source = accountRepository.findAccountByAccountNumber(transferData.getSourceAccount().getAccountNumber());
		Optional<Account> recipient = accountRepository.findAccountByAccountNumber(transferData.getRecipientAccount().getAccountNumber());			
		
		BigDecimal balanceFinalSource = source.get().getBalance().subtract(transferData.getDrawBalance());
		BigDecimal balanceFinalCreditSource = source.get().getCredit().subtract(transferData.getDrawCredit());		
		BigDecimal balanceFinalReceipient = recipient.get().getBalance().add(transferData.getAmount());
		
		source.get().setBalance(balanceFinalSource);
		source.get().setCredit(balanceFinalCreditSource);
		recipient.get().setBalance(balanceFinalReceipient);
		accountRepository.save(source.get());
		accountRepository.save(recipient.get());
		transferRepository.save(transferData);
	}
	
}
