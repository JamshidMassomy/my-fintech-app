package com.fintech.db_layer_test;

import com.fintech.domain.Account;
import com.fintech.repository.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

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
    public void testAccountBalance() {
        String myIban = "EE89370400440532013000";
        BigDecimal amount = BigDecimal.valueOf(1.0);
        accountMapper.updateAccount(myIban, amount);
        Account updatedAccount = accountMapper.findByIBAN(myIban);
        assertThat(updatedAccount.getBalance()).
                isPositive().isLessThan(BigDecimal.valueOf(997.50)).
                isGreaterThan(BigDecimal.valueOf(995.50));
    }
}
