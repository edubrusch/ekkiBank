package com.eduardo.ekki.ekkiTransfer.service;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public interface TransferProcessService {
	
	TransferResult processTransferHasFunds(Transfer transfer);
	
	TransferResult processTransferOverrideRecentTransfer(Transfer transfer, Transfer previousTransfer);
	
	TransferResult processTransferAskForConfirmation(Transfer transfer);
	
	TransferResult processTransferAskForConfirmationAndOverrideRecent(Transfer transfer, Transfer previousTransfer);	
	
	TransferResult processTransferConfirmation(Transfer transfer);
	
}
