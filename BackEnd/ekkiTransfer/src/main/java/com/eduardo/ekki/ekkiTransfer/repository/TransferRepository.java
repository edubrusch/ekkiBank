package com.eduardo.ekki.ekkiTransfer.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, String> {
	
//	@Query("select COUNT(transferID)"
//			+ " FROM transfer_history th "
//			+ " WHERE th.source_Account = ?1"
//			+ " AND th.destination_Account = ?2"
//			+ " AND th.value = ?3")
//	public Optional<Transfer> findTransferBySourceAccountByDestinationAccountByAmountByTransferDate(
//			String sourceAcount,
//			String destinationAccount, 
//			BigDecimal amount,
//			Date transferDate
//			);

//	@Query("Select transfer.transferID from transfer th where th.amount =:amount")
//	public Set<Transfer> find(@Param("amount") BigDecimal amount);
	
	@Query("Select t.transferID from Transfer t where t.amount =:amount")
	public Set<Transfer> find(@Param("amount") BigDecimal amount);
	
	
}