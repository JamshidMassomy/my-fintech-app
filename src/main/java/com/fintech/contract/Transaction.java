package com.fintech.contract;

import com.fintech.dto.TransactionRequestDto;
import com.fintech.dto.TransactionResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface Transaction {
    TransactionResponseDto processTransaction(TransactionRequestDto transactionRequestDto);
}
