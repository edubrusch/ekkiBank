package com.eduardo.ekki.ekkiTransfer.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, Query> {	

	@Query(value = 
			"Select t.*"
			+ " from transfer_history t"	
			+ " where t.source_account = ?1"
			+ " and t.recipient_account = ?2"
			+ " and t.amount = ?4"
			+ " and t.transfer_date < ?3"
			+ " order by t.transfer_date desc"
			, nativeQuery = true)
	public Optional<Transfer> find(
			String sourceAccount,
			String recipientAccount,
			LocalDateTime date,
			BigDecimal amount);
	
	
	public Optional<Transfer> findByTransferID(String transferID);
	
}