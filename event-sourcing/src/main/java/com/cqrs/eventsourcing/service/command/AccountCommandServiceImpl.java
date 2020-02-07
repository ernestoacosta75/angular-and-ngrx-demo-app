package com.cqrs.eventsourcing.service.command;

import com.cqrs.eventsourcing.command.CreateAccountCommand;
import com.cqrs.eventsourcing.command.CreditMoneyCommand;
import com.cqrs.eventsourcing.command.DebitMoneyCommand;
import com.cqrs.eventsourcing.dto.AccountCreateDTO;
import com.cqrs.eventsourcing.dto.MoneyCreditDTO;
import com.cqrs.eventsourcing.dto.MoneyDebitDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The <b>CommandGateway<b/> interface provided by Axon, allow us to
 * dispatch commands.
 * When we wire up the <b>CommandGateway<b/> as below, Axon will actually
 * provide the <b>DefaultCommandGateway<b/> implementation.
 *
 * Then, using the send method on the CommandGateway we can send a
 * command and wait for the response.
 */
@AllArgsConstructor(staticName = "of")
public class AccountCommandServiceImpl implements AccountCommandService {

    private final CommandGateway commandGateway;

    @Override
    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO) {
        return commandGateway.send(new CreateAccountCommand(UUID.randomUUID().toString(),
                                            accountCreateDTO.getStartingBalance(),
                                            accountCreateDTO.getCurrency()));
    }

    @Override
    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO) {
        return commandGateway.send(new CreditMoneyCommand(accountNumber,
                                            moneyCreditDTO.getCreditAmount(),
                                            moneyCreditDTO.getCurrency()));
    }

    @Override
    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO) {
        return commandGateway.send(new DebitMoneyCommand(accountNumber,
                                            moneyDebitDTO.getDebitAmount(),
                                            moneyDebitDTO.getCurrency()));
    }
}
