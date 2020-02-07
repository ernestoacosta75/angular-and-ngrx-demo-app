package com.cqrs.eventsourcing.service.command;

import com.cqrs.eventsourcing.dto.AccountCreateDTO;
import com.cqrs.eventsourcing.dto.MoneyCreditDTO;
import com.cqrs.eventsourcing.dto.MoneyDebitDTO;

import java.util.concurrent.CompletableFuture;

/**
 * This interface will be used to handle the Commands.
 *
 * To be implemented.
 */
public interface AccountCommandService {
    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO);
    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO);
    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO);
}
