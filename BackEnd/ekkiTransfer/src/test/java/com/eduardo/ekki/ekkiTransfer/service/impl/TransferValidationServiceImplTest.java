package com.eduardo.ekki.ekkiTransfer.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.eduardo.ekki.ekkiTransfer.common.MessageStringsEnum;
import com.eduardo.ekki.ekkiTransfer.entity.Account;
import com.eduardo.ekki.ekkiTransfer.repository.TransferRepository;
import com.eduardo.ekki.ekkiTransfer.service.TransferProcessService;
import com.eduardo.ekki.ekkiTransfer.service.TransferValidationService;
import com.eduardo.ekki.ekkiTransfer.service.result.TransferResult;


@SpringBootTest
public class TransferValidationServiceImplTest {
	
	private TransferValidationService tranferValidator;
	private Account noCreditccount;
	private Account hasCreditAccount;
	
	@Mock
	private TransferProcessService processService;
	
	@Mock 
	private TransferRepository transferRepository; 
	
	@BeforeEach
	public void prepareTest () {
		
		hasCreditAccount = Account.builder()
				.accountNumber(123)
				.balance(new BigDecimal(500))
				.hasCredit(true)
				.credit(new BigDecimal(5000))
				.build();
		noCreditccount = Account.builder()
				.accountNumber(456)
				.balance(new BigDecimal(500))
				.hasCredit(false)
				.credit(new BigDecimal(0))
				.build();
		
		tranferValidator = new TransferValidationServiceImpl(transferRepository, processService);
	}

	
	@Test
	public void shouldFailNoFundsProcessTransfer() {
		BigDecimal amount = new BigDecimal(10000);
		String expectedMessage = String.format(MessageStringsEnum.ERROR_NOT_APPROVED_NO_FUNDS_CREDIT.get(), hasCreditAccount.getAccountNumber());
		
		TransferResult validatorResult = tranferValidator.validateTransferCash(hasCreditAccount, noCreditccount, amount); 
		
		assertThat(validatorResult.getMessage()).contains(expectedMessage);
	}
	
	@Test
	public void shouldFailAskCreditCard() {
		BigDecimal amount = new BigDecimal(10000);
		String expectedMessage = String.format(MessageStringsEnum.ERROR_NOT_APPROVED_NEEDS_CREDITCARD.get(), noCreditccount.getAccountNumber());
		
		TransferResult validatorResult = tranferValidator.validateTransferCash(noCreditccount, hasCreditAccount, amount); 
		
		assertThat(validatorResult.getMessage()).contains(expectedMessage);
	}

}
