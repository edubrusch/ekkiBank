package com.eduardo.ekki.ekkiTransfer.service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public interface TransferResultProcessService {
	
	TransferResult getFailureOutput(MessageStrings reason, MessageStrings cause, String acccount);
	
	TransferResult getSuccessfulOutput(MessageStrings reason, Transfer transfer);

}
