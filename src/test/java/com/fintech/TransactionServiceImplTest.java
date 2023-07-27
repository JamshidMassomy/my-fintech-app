package com.fintech;

import com.fintech.domain.Account;
import com.fintech.repository.mapper.AccountMapper;
import com.fintech.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
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




}
