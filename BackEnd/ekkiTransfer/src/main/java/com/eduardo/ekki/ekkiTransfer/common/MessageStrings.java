package com.eduardo.ekki.ekkiTransfer.common;

public enum MessageStrings {
	
	ERROR_ACCOUNT_NOT_FOUND_PARAM_ACCOUNT("Account number %s was not found."),
	ERROR_TRANSFER_NOT_COMPLETED("Transfer not complete for account %s."),
	ERROR_TRANSFER_SOURCE_ACCOUNT_NOT_FOUND("Source account not found for transfer."),
	ERROR_TRANSFER_RECIPIENT_ACCOUNT_NOT_FOUND("Recipient account not found for transfer."),	
	ERROR_TRANSFER_ERROR("Error Initializing transfer. Please contact the development with this message."),
	
	ERROR_TRANSFER_NOT_POSSIBLE("Transfer is not possible to be completed to account %s."),
	ERROR_NOT_APPROVED_NEEDS_CREDITCARD("Account %s does not have enough founds and there is no credit. Would you like ask for a credit?"),
	ERROR_NOT_APPROVED_NO_FUNDS_CREDIT("Account %s does not have enough founds for the transfer neither the credit has enough funds."),
	
	APPROVED_OVERRIDE_NEEDS_PASSWORD("it Has been found recent transfer with the same account and value. please confirm transacation"),
	APPROVED_OVERRIDE_RECENT_TRANSACTION("it Has been found recent transfer with the same account and value. "
			+ "that transfer will be cancelled and a new transfer will take it's place."),
	
	APPROVED_TRANSFER("Transfer complete frorm account %s to account %s with value %s."),
	APPROVED_NOT_COMPLETED("Transfer approved but not completed for account %s."),

	SUCCESS_ACCOUNT_FOUND("Account number %s was found."),
	SUCCESS_NEEDS_CONFIRMATION("Transfer is over the safe limit, please confirm the transaction"),
	SUCCESS_TRANSFER_ACCOUNT("Transfer success. Transfered from account %s to accont %s value of %s"),	
	SUCCESS_APPROVED_USED_CREDICART_LOAM("Transfer automatically used funds from user's credit card due to lack in the funds of the user's account.");
	
	private String value;
	
	private MessageStrings(String message) {
		value = message;
	}
	
	public String get() {
		return value;
	}

}
