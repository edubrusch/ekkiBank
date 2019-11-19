package com.eduardo.ekki.ekkiTransfer.service;

import com.eduardo.ekki.ekkiTransfer.domain.Account;

public interface TransferService {
	
	public void transferCash(Account source, Account recipient);

}
