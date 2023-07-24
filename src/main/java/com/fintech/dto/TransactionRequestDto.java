package com.fintech.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransactionRequestDto {

    private String fromIBAN;
    private String toIBAN;
    private Double amount;

}
