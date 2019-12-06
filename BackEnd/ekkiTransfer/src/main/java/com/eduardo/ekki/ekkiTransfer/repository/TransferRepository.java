package com.eduardo.ekki.ekkiTransfer.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, Query> {	

	@Query("Select t"
			+ " from Transfer t"
			+ " where t.sourceAccount =:source"
			+ " and t.destinationAccount =:destination"
			+ " and t.amount =:amount"
			+ " and t.transferDate < :date"
			+ " order by t.transferDate desc")
	public Optional<Transfer> find(
			@Param("source") String sourceAccount,
			@Param("destination") String recipientAccount,
			@Param("date") LocalDateTime date,
			@Param("amount") BigDecimal amount);
	
	
	public Optional<Transfer> findByID(String transferID);
	
}