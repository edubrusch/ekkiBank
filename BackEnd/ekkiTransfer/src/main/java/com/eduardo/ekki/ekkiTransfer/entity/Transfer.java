package com.eduardo.ekki.ekkiTransfer.entity;

import java.math.BigDecimal;
import java.util.Date;

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
	
	private String destinationAccount;
	
	private BigDecimal amount;
	
	private TransferStatus status;
	
	private Date transferDate;

}
