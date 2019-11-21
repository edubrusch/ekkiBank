package com.eduardo.ekki.ekkiTransfer.service.result;

import java.math.BigDecimal;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBalanceResult{
	
	private boolean sucess;
	private String message;
	private long accountNumber;
	private BigDecimal balance;
	
	
	public static AccountBalanceResult processFailure(MessageStrings string, long accountID) {
			
			return AccountBalanceResult.builder()
			.sucess(false)
			.accountNumber(accountID)
			.message(String.format(string.get(), accountID))
			.build();		
			
		}
	
	public static AccountBalanceResult processSuccess(MessageStrings string, long accountID) {
		
		return AccountBalanceResult.builder()
				.sucess(false)
				.accountNumber(accountID)
				.message(String.format(string.get(), accountID))
				.build();
	}
	
}
