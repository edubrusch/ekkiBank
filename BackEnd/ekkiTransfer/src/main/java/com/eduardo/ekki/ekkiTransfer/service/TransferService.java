package com.eduardo.ekki.ekkiTransfer.service;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

public interface TransferService {
	
	public TransferResult transferCash(Account source, Account recipient);

}
