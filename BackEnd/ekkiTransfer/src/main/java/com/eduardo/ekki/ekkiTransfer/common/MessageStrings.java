package com.eduardo.ekki.ekkiTransfer.common;

public enum MessageStrings {
	
	ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT("Account number %s was not found."),	
	ERROR_TRANSFER_NOT_COMPLETED("Transfer not complete."),
	ERROR_TRANSFER_SOURCE_ACCOUNT_NOT_FOUND("Source account not found for transfer."),
	ERROR_TRANSFER_RECIPIENT_ACCOUNT_NOT_FOUND("Recipient account not found for transfer."),
	
	
	SUCCESS_ACCOUNT_FOUND("Account number %s was found."),
	SUCCESS_TRANSFER_ACCOUNT("Transfer success. Transfered from account %s to accont %s valueof %s"); 
	
	
	private String value;
	
	private MessageStrings(String message) {
		value = message;
	}
	
	public String get() {
		return value;
	}

}
