package com.eduardo.ekki.ekkiTransfer.service.impl;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;
import com.eduardo.ekki.ekkiTransfer.service.TransferResultProcessService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

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
		
		return TransferResult
				.builder()
				.sucess(false)
				.message(reason.get())
				.transfer(transfer)
				.build();
	}

}
