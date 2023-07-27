package com.fintech.repository.mapper;

import com.fintech.domain.Account;
import org.apache.ibatis.annotations.*;
import java.math.BigDecimal;


@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM account WHERE IBAN = #{IBAN}")
    Account findByIBAN(@Param("IBAN") String IBAN);

    @Update("UPDATE account SET balance = #{amount} WHERE IBAN = #{IBAN}")
    void updateAccount(@Param("IBAN") String IBAN, @Param("amount") BigDecimal amount);

}
