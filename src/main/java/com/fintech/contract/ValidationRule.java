package com.fintech.contract;

import com.fintech.dto.TransactionRequestDto;

import java.util.function.Function;

@FunctionalInterface
public interface ValidationRule extends Function<TransactionRequestDto, Boolean> {}
