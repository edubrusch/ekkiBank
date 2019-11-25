package com.eduardo.ekki.ekkiTransfer.service.result;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;

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
	private Transfer transfer;
}
