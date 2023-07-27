package com.fintech.contract;

import com.fintech.dto.TransactionRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface Transaction {
    boolean processTransaction(TransactionRequestDto transactionRequestDto,  String sessionId);
}
