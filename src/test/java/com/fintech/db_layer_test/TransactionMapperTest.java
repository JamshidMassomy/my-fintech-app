package com.fintech.db_layer_test;

import com.fintech.domain.Currency;
import com.fintech.domain.Transaction;
import com.fintech.domain.TransactionStatus;
import com.fintech.repository.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void testTransactionSavesData() {
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(Currency.EUR);
        transaction.setCreditorIBAN("test Iban");
        transaction.setDebtorIBAN("");
        transaction.setAmount(BigDecimal.valueOf(1.0));
        transaction.setDateTime(LocalDateTime.now());
        transaction.setReference("");
        transaction.setDescription("sd");
        transaction.setCreatedSessionId("");
        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        TransactionMapper transactionMapperMock = mock(TransactionMapper.class);

        transactionMapperMock.saveTransaction(transaction);
        verify(transactionMapperMock, times(1)).saveTransaction(transactionArgumentCaptor.capture());


        assertThat(transactionArgumentCaptor.getValue().getCreditorIBAN())
                .isNotNull();
        assertThat(transactionArgumentCaptor.getValue().getCurrency()).isSameAs(Currency.EUR);
    }

    @Test
    public void shouldThrow() {
        Transaction transaction = new Transaction();
        transaction.setStatus(null);
        transaction.setCurrency(Currency.EUR);
        transaction.setCreditorIBAN("test");
        transaction.setDebtorIBAN("test");
        transaction.setAmount(null);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setReference(null);
        transaction.setDescription(null);
        transaction.setCreatedSessionId("");
        assertThrows(Exception.class, () -> transactionMapper.saveTransaction(transaction));
    }

}
