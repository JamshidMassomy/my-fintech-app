package com.fintech.service_layer_test;

import com.fintech.domain.Account;
import com.fintech.domain.Currency;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.exception.AccountNotFoundException;
import com.fintech.exception.UnSupportedCurrencyException;
import com.fintech.repository.mapper.AccountMapper;
import com.fintech.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {


    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountMapper accountMapper;


    @Test
    void tesWithDraw() {
        // Create an Account with a balance of 100.34
        Account account = new Account();
        account.setIBAN("testIBAN");
        account.setBalance(BigDecimal.valueOf(100.34));
        transactionService.withDraw(account, BigDecimal.valueOf(20.01));
        verify(accountMapper).updateAccount("testIBAN", BigDecimal.valueOf(80.33));
    }

    @Test
    void testDeposit() {
        // Create an Account with a balance of 100.34
        Account account = new Account();
        account.setIBAN("testIBAN");
        account.setBalance(BigDecimal.valueOf(100.34));
        transactionService.deposit(account, BigDecimal.valueOf(20.01));
        verify(accountMapper).updateAccount("testIBAN", BigDecimal.valueOf(120.35));
    }


    @Test
    public void shouldThrowIfNoMatchingAccountFound() {
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAmount(BigDecimal.valueOf(5));
        requestDto.setCurrency(Currency.EUR);
        requestDto.setDebtorIBAN("correct IBAN");
        requestDto.setCreditorIBAN("correct IBAN");
        requestDto.setDescription("my test transactions");
        requestDto.setReference("ref00912");
        assertThrows(AccountNotFoundException.class, ()-> transactionService.processTransaction(requestDto,"testId"));
    }

    @Test
    public void shouldThrowUnsupportedCurrencyException() {
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAmount(BigDecimal.valueOf(5));
        requestDto.setCurrency(Currency.USD);
        requestDto.setDebtorIBAN("wrong account");
        requestDto.setCreditorIBAN("wrong account");
        requestDto.setDescription("my test transactions");
        requestDto.setReference("ref00912");
        assertThrows(UnSupportedCurrencyException.class, ()-> transactionService.processTransaction(requestDto,"testId"));
    }


    @Test
    public void shouldReturnCorrectIban() {
        String myIban = "EE89370400440532013000";
        Account account = accountMapper.findByIBAN(myIban);
        assertThat(account).isNotNull().isInstanceOf(Account.class);
        assertThat(account.getIBAN()).
                isNotNull()
                .isNotEmpty()
                .matches("EE89370400440532013000")
                .startsWith("EE")
                .hasSizeGreaterThan(1);
    }

    @Test
    public void testIfParametersAreValid() {
        ArgumentCaptor<TransactionRequestDto> requestDtoArgumentCaptor = ArgumentCaptor.forClass(TransactionRequestDto.class);
        ArgumentCaptor<String> sessionId = ArgumentCaptor.forClass(String.class);
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAmount(BigDecimal.valueOf(5));
        requestDto.setCurrency(Currency.EUR);
        requestDto.setDebtorIBAN("EE89370400440532013000");
        requestDto.setCreditorIBAN("EE1420041010050500013M02606");
        requestDto.setDescription("my test transactions");
        requestDto.setReference("ref00912");
        transactionService.isTransactionProcessed(requestDto, "2jamshfd");
        verify(transactionService).isTransactionProcessed(requestDtoArgumentCaptor.capture(), sessionId.capture());
        assertThat(requestDtoArgumentCaptor.getValue().getCreditorIBAN()).
                isNotBlank().
                isNotEmpty().
                isEqualTo("EE1420041010050500013M02606")
                .isNotNull()
                .startsWith("EE");

        assertThat(requestDtoArgumentCaptor.getValue().
                getAmount()).
                isNotNull().
                isPositive().isGreaterThan(BigDecimal.ZERO)
                .isNotIn(0, -1);

    }


}
