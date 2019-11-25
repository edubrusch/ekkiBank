package com.eduardo.ekki.ekkiTransfer.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, String> {
	
	@Query("select COUNT(transferID)"
			+ " FROM transfer_history th "
			+ " WHERE th.sourceAccount = sourceAccountNumber"
			+ " AND th.destinationAccount = recipientAccountNumber"
			+ " AND th.transferDate <= : refenceDate")
	public Optional<Transfer> findAccountByAccountNumber(
			@Param("sourceAccountNumber") String sourceAccountNumber,
			@Param("recipientAccountNumber") String recipientAccountNumber, 
			@Param("value") BigDecimal value,
			@Param("refenceDate") Date twoMinutesAgo);

}