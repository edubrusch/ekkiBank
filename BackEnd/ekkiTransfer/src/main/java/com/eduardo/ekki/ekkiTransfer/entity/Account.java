package com.eduardo.ekki.ekkiTransfer.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAccount;
	
	private String name;
		
	private String accountNumber;
	
	private BigDecimal balance;
	
	private BigDecimal credit;
	
	private boolean hasCredit;

}
