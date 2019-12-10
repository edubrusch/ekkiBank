package com.eduardo.ekki.ekkiTransfer.common;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult.TransferResultBuilder;

public class TransferResultProcess{
	
	public TransferResult getFailureOutput(MessageStringsEnum reason, MessageStringsEnum cause, long account) {
		
		String message = String.format(reason.get(), account) + " " + String.format(cause.get(), account);

		return TransferResult
				.builder()
				.sucess(false)
				.message(message)
				.build();
	}

	public TransferResult getSuccessfulOutput(MessageStringsEnum reason, Transfer transfer) {
		
		String message = String.format(reason.get(),
				transfer.getSourceAccount().getAccountNumber(),
				transfer.getRecipientAccount().getAccountNumber(),
				transfer.getAmount());
		
		TransferResultBuilder output =  TransferResult.builder()
				.sucess(true)
				.message(message)				
				.sourceAccount(transfer.getSourceAccount().getAccountNumber())
				.recipientAccount(transfer.getRecipientAccount().getAccountNumber())
				.amount(transfer.getAmount())
				.drawBalance(transfer.getDrawBalance())
				.drawCredit(transfer.getDrawCredit())
				.status(transfer.getStatus())
				.transferDate(transfer.getTransferDate());
		
		if(transfer.getPreviousTransferID() == null) {
			output.previousTransferID("No previous transfer");
		} else {
			output.previousTransferID(transfer.getPreviousTransferID());
		}		
		
		return output.build();
	}

}
