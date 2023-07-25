package com.fintech.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class TransactionRequestDto {

    @NotBlank(message = "From account number must not be blank")
    private String debtorIBAN;

    @NotBlank(message = "To account number must not be blank")
    private String creditorIBAN;

    @Positive(message = "Transaction amount must be positive or greater then zero (0) ")
    private BigDecimal amount;

//    @NotBlank(message = "Currency must not be blank")
//    private Currency currency;

    private String description;

    private String reference;

}
