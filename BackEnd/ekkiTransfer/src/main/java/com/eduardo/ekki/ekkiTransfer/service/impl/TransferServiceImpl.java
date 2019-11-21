package com.eduardo.ekki.ekkiTransfer.service.impl;

import org.springframework.stereotype.Service;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.service.TransferService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;

@Service
public class TransferServiceImpl implements TransferService{

	@Override
	public TransferResult transferCash(Account source, Account recipient) {
		return null;
	}

}
