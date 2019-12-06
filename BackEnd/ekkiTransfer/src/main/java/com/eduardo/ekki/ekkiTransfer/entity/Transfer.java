package com.eduardo.ekki.ekkiTransfer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "transfer_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transferID;
	
	private String sourceAccount;
	
	private String recipientAccount;
	
	private BigDecimal amount;
	
	private BigDecimal drawBalance;
	
	private BigDecimal drawCredit;
	
	private TransferStatus status;
	
	private LocalDateTime transferDate;
	
	private String previousTransferID;
	
}
