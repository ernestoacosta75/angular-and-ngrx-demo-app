package com.cqrs.eventsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This DTO is used for debiting money
 * from an account.
 */
@Getter
@Setter
@NoArgsConstructor
public class MoneyDebitDTO {

    private double debitAmount;
    private String currency;
}
