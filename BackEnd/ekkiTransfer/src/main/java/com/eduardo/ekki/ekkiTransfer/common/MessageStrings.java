package com.eduardo.ekki.ekkiTransfer.common;

public enum MessageStrings {
	
	ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT("Account number %s was not found"), 
	ERROR_TRANSFER_NOT_COMPLETED("Transfer not complete");
	
	private String value;
	
	private MessageStrings(String message) {
		value = message;
	}
	
	public String get() {
		return value;
	}

}
