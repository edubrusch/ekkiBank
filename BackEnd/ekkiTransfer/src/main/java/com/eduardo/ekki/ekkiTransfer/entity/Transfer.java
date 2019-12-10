package com.eduardo.ekki.ekkiTransfer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "transfer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transferID;
	
	@NotNull
	private String sourceAccount;
	
	@NotNull
	private String recipientAccount;
	
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private BigDecimal drawBalance;
	
	@NotNull
	private BigDecimal drawCredit;
	
	@NotNull
	private TransferStatus status;
	
	@NotNull
	private LocalDateTime transferDate;	
	
	private String previousTransferID;
	
}
