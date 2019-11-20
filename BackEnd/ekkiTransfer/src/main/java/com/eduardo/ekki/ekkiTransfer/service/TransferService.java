package com.eduardo.ekki.ekkiTransfer.service;

import com.eduardo.ekki.ekkiTransfer.domain.Account;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public interface TransferService {
	
	public TransferResult transferCash(Account source, Account recipient);

}
