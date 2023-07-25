package com.fintech.util;

import com.fintech.contract.AccountValidationRule;
import com.fintech.repository.AccountMapper;
import lombok.RequiredArgsConstructor;

import java.util.Objects;


public final class ValidateAccount {

    public static AccountValidationRule isAccountExists() {
        return accounts -> {
            return accounts.stream().allMatch(Objects::nonNull);
        };
    }

}
