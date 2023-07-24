package com.fintech.service;

import com.fintech.contract.ITransaction;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.dto.TransactionResponseDto;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements ITransaction {

    @Override
    public TransactionResponseDto processTransaction(final TransactionRequestDto transactionRequestDto) {
        return null;
    }
}
