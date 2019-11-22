package com.eduardo.ekki.ekkiTransfer.service.result;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResult {
	
	private boolean sucess;
	private String message;
	private long sourceAccount;
	private long destinaionAccount;
	private BigDecimal value;
}
