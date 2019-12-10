package com.eduardo.ekki.ekkiTransfer.service.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
	
	private long sourceAccount;
	private long recipientAccount;
	private BigDecimal amount;

}
