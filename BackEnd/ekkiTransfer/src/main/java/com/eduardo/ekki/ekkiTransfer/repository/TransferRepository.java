package com.eduardo.ekki.ekkiTransfer.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eduardo.ekki.ekkiTransfer.entity.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, Query> {	

	@Query(value = 
			"Select top 1 t.*"
			+ " from transfer t"
			+ " inner join account s"
			+ " on s.id_Account = t.id_source_account"
			+ " inner join account r"
			+ " on r.id_Account = t.id_recipient_account"	
			+ " where s.account_number = ?1"
			+ " and r.account_number = ?2"
			+ " and t.amount = ?3"
			+ " and (TIMESTAMPDIFF (MINUTE, t.transfer_date, LOCALTIMESTAMP)) < 2"
			+ " order by t.transfer_date desc"
			, nativeQuery = true)
	public Optional<Transfer> find(
			long sourceAccount,
			long recipientAccount,
			BigDecimal amount
			);
	
	public Optional<Transfer> findByTransferID(long transferID);
	
}
