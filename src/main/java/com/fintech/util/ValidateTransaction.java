package com.fintech.util;

import com.fintech.contract.ValidationRule;

import java.math.BigDecimal;

public class ValidateTransaction {

    public static ValidationRule isAmountGreaterThanZero() {
        return transaction -> transaction.getAmount().compareTo(BigDecimal.ZERO) > 0;
    }

}
