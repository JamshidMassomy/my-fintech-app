package com.fintech.repository;

import com.fintech.domain.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransactionMapper {
    Transaction saveTransaction(@Param("transaction") Transaction transaction);
}
