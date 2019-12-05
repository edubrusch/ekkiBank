package com.eduardo.ekki.ekkiTransfer.service;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationDTO;

@Service
public interface TransferProcessService {
	
	TransferResult processTransferHasFunds(TransferValidationDTO transferDTO);
	
	TransferResult processTransferOverrideRecentTransfer(TransferValidationDTO transferDTO);
	
	TransferResult processTransferAskForConfirmation(TransferValidationDTO transferDTO);
	
	TransferResult processTransferAskForConfirmationAndOverrideRecent(TransferValidationDTO transferDTO);

	TransferResult processTransferUseCredit(TransferValidationDTO transferDTO);
	
	
}
