package com.eduardo.ekki.ekkiTransfer.service;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;
import com.eduardo.ekki.ekkiTransfer.service.validation.TransferValidationDTO;

@Service
public interface TransferProcessService {
	
	TransferResult processTransferAccount(TransferValidationDTO transferValidation);

}
