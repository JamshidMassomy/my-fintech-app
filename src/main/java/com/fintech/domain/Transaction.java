package com.fintech.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "status", "fromIBAN", "toIBAN"})
@EqualsAndHashCode(of = {"id"})
@Getter
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

}
