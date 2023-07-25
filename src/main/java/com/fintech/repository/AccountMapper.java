package com.fintech.repository;

import com.fintech.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AccountMapper {
    Account findByIBAN(@Param("IBAN") String IBAN);
    void updateAccount(@Param("IBAN") String IBAN, @Param("amount") BigDecimal amount);
    BigDecimal fetchBalance(@Param("IBAN") String IBAN);
    List<Account> areAccountsExist(@Param("IBANs") List<String> IBANs);
}
