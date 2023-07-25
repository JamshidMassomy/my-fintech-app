package com.fintech.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "status", "fromIBAN", "toIBAN"})
@EqualsAndHashCode(of = {"id"})
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    TransactionStatus status;

    @Column(name = "from_iban", nullable = false)
    private String fromIBAN;

    @Column(name = "to_iban", nullable = false)
    private String toIBAN;

    @Column(name = "amount")
    private BigDecimal amount;

    private LocalDateTime dateTime;

    private String description;
    private String reference;


}
