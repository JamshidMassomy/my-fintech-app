package com.fintech.domain;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;


@Builder
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "iban")
    private String IBAN;

    @Column(name = "account_name")
    private String accountName;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

}
