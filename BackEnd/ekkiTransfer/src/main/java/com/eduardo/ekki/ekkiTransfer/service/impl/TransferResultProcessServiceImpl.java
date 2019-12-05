package com.eduardo.ekki.ekkiTransfer.service.impl;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferResultProcessServiceImpl implements TransferResultProcessService {

	@Override
	public TransferResult getFailureOutput(MessageStrings reason, MessageStrings cause, String acccount) {
		
		String message = String.format(reason.get(), acccount) + " " + reason.get();

		return TransferResult
				.builder()
				.sucess(false)
				.message(message)
				.build();
	}

	@Override
	public TransferResult getSuccessfulOutput(MessageStrings reason, Transfer transfer) {
		
		String message = String.format(reason.get(),
				transfer.getSourceAccount(),
				transfer.getDestinationAccount(),
				transfer.getAmount());
		
		return TransferResult
				.builder()
				.sucess(true)
				.message(message)
				.transfer(transfer)
				.build();
	}

}
