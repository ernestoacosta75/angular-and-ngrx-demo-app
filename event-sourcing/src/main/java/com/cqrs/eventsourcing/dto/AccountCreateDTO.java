package com.cqrs.eventsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This DTO is used to create a new account.
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountCreateDTO {

    private double startingBalance;
    private String currency;
}
