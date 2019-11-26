package com.eduardo.ekki.ekkiTransfer.service;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public interface TransferResultProcessService {
	
	TransferResult getFailureOutput(MessageStrings reason, MessageStrings cause, String acccount);
	
	TransferResult getSuccessfulOutput(MessageStrings reason, Transfer transfer);

}
