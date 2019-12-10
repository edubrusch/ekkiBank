package com.eduardo.ekki.ekkiTransfer.common;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public class TransferResultProcess{
	
	public TransferResult getFailureOutput(MessageStrings reason, MessageStrings cause, String acccount) {
		
		String message = String.format(reason.get(), acccount) + " " + reason.get();

		return TransferResult
				.builder()
				.sucess(false)
				.message(message)
				.build();
	}

	public TransferResult getSuccessfulOutput(MessageStrings reason, Transfer transfer) {
		
		String message = String.format(reason.get(),
				transfer.getSourceAccount(),
				transfer.getRecipientAccount(),
				transfer.getAmount());
		
		return TransferResult
				.builder()
				.sucess(true)
				.message(message)
				.transfer(transfer)
				.build();
	}

}
