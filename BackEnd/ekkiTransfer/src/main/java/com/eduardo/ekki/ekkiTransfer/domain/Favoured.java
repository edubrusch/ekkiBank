package com.eduardo.ekki.ekkiTransfer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Favoured {	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFavoured;
	
	private String name;
	
	private String documentID;
	
	private String bank;
	
	private String acountNumber;


}
