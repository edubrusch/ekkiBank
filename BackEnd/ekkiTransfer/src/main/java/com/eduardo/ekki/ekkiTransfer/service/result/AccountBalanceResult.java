package com.eduardo.ekki.ekkiTransfer.service.result;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccountBalanceResult{
	
	private boolean sucess;
	private long accountNumber;
	private BigDecimal balance;
	
}
