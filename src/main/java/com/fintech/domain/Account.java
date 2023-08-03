package com.fintech.domain;

import lombok.*;
import java.math.BigDecimal;

@Builder
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {

    private Long id;
    private String IBAN;
    private String accountName;
    private BigDecimal balance;
    private Currency currency;

}
