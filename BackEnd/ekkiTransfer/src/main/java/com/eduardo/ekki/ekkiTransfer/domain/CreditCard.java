package com.eduardo.ekki.ekkiTransfer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CreditCard {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idCreditCard;
	
	private String serial;
	
	private int CVV;
	
	private String dueDate ;
	
	private String acount;
	

}
