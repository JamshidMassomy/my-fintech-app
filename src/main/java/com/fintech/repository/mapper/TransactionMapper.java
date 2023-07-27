package com.fintech.repository.mapper;

import com.fintech.domain.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transactions (status, currency, creditor_iban, debtor_iban, amount, date_time, description, reference, created_session_id )\n" +
            "VALUES (#{status}, #{currency}, #{creditorIBAN}, #{debtorIBAN}, #{amount}, #{dateTime}, #{description}, #{reference}, #{createdSessionId} )")
    void saveTransaction(Transaction transaction);
}
