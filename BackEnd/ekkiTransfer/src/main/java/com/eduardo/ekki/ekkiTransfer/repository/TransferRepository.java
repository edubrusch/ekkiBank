package com.eduardo.ekki.ekkiTransfer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, Query> {	

	@Query("Select t.transferID "
			+ "from Transfer t "
			+ "where t.sourceAccount =:source "
			+ "and t.destinationAccount =:destination "
			+ "and t.transferDate < CURRENT_TIMESTAMP")
	public Optional<Transfer> find(
			@Param("source") String sourceAccount,
			@Param("destination") String recipientAccount);
	
}