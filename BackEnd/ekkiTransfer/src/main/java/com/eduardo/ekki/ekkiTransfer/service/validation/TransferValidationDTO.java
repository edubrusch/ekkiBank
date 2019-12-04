package com.eduardo.ekki.ekkiTransfer.service.validation;

import java.math.BigDecimal;

import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.entity.Transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferValidationDTO {
	
	private Account sourceAccount;
	private Account recipientAccount;
	private Transfer previousTransfer;
	private BigDecimal amount;
	private BigDecimal drawBalance;
	private BigDecimal drawCredit;
	TransferValidationStatus status;

}
