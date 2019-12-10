package com.eduardo.ekki.ekkiTransfer.service.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eduardo.ekki.ekkiTransfer.common.TransferStatusEnum;

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
	
	private long recipientAccount;
		
	private BigDecimal amount;
		
	private BigDecimal drawBalance;
		
	private BigDecimal drawCredit;
		
	private TransferStatusEnum status;
		
	private LocalDateTime transferDate;	
	
	private String previousTransferID;
	
}
