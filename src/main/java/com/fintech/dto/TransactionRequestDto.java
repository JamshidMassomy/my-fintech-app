package com.fintech.dto;

import com.fintech.domain.Currency;
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

    private Currency currency;

    @NotBlank(message = "description must not be blank")
    private String description;

    @NotBlank(message = "reference must not be blank")
    private String reference;

}
