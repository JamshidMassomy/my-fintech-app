package com.fintech.contract;


import com.fintech.domain.Account;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface AccountValidationRule extends Function<List<Account>, Boolean> { }
