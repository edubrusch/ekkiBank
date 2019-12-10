package com.eduardo.ekki.ekkiTransfer.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, Query> {	

	@Query(value = 
			"Select top 1 t.*"
			+ " from transfer_history t"	
			+ " where t.source_account = ?1"
			+ " and t.recipient_account = ?2"
			+ " and t.amount = ?3"
			+ " and (TIMESTAMPDIFF (MINUTE, t.transfer_date, LOCALTIMESTAMP)) < 2"
			+ " order by t.transfer_date desc"
			, nativeQuery = true)
	public Optional<Transfer> find(
			String sourceAccount,
			String recipientAccount,
			BigDecimal amount
			);
	
	public Optional<Transfer> findByTransferID(String transferID);
	
}