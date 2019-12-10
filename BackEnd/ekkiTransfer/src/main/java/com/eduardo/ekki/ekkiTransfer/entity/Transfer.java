package com.eduardo.ekki.ekkiTransfer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.eduardo.ekki.ekkiTransfer.common.TransferStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transferID;
	
	@ManyToOne	
	@JoinColumn(name = "idSourceAccount")
	private Account sourceAccount;
	
	@ManyToOne	
	@JoinColumn(name = "idRecipientAccount")
	private Account recipientAccount;
		
	private BigDecimal amount;
		
	private BigDecimal drawBalance;
		
	private BigDecimal drawCredit;
		
	private TransferStatusEnum status;
		
	private LocalDateTime transferDate;	
	
	private String previousTransferID;
	
}
