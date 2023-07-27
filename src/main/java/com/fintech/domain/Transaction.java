package com.fintech.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(of = {"id", "status", "debtorIBAN", "creditorIBAN"})
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Transaction {

    private Long id;
    private TransactionStatus status;
    private Currency currency;
    private String creditorIBAN;
    private String debtorIBAN;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String description;
    private String reference;
    private String createdSessionId;

}
