package com.cqrs.eventsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This DTO is used for crediting money
 * to an account.
 */
@Getter
@Setter
@NoArgsConstructor
public class MoneyCreditDTO {

    private double creditAmount;
    private String currency;
}
