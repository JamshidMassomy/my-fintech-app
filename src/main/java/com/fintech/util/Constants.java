package com.fintech.util;

import org.springframework.stereotype.Component;

@Component
public final class Constants {

    public static final String INVALID_NUMBER =
            "Amount must be greater than zero.";
    public static final String INSUFFICIENT_ACCOUNT_BALANCE =
            "Account does not have sufficient balance";

    public static final String INVALID_CURRENCY =
            "Only EUR currency is supported";

    public static final String NO_MATCHING_ACCOUNT =
            "No matching accounts found please check input details ";
    public static final String FAILED_TRANSACTION =
            "Failed to perform transaction ... please try again latter :( ";

    public static final String SESSION_ID_HEADER = "X-Session-Id";
}
