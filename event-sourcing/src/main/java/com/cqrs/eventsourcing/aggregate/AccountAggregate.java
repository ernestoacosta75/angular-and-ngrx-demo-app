package com.cqrs.eventsourcing.aggregate;

import com.cqrs.eventsourcing.command.CreateAccountCommand;
import com.cqrs.eventsourcing.command.CreditMoneyCommand;
import com.cqrs.eventsourcing.command.DebitMoneyCommand;
import com.cqrs.eventsourcing.event.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * This entity will act as the Account aggregate.
 *
 * The @Aggregate annotation tells Axon that this entity
 * will be managed by Axon. Basically, this is similar to
 * <b>@Entity annotation<b/>.
 *
 * <b>@AggregateIdentifier<b/> annotation is used for the identifying
 * a particular instance of the Aggregate. In other words, this
 * is similar to JPA's  <b>@Id<b/> annotation.
 *
 * The command handlers and event handlers are methods on the
 * Aggregate that should be invoked for a particular command or
 * an event.
 *
 * Due to their relation to the Aggregate, it is recommended to
 * define the handlers in the Aggregate class itself.
 * Also, the command handlers often need to access the state of
 * the Aggregate.
 *
 * In this Aggregate, we're handling the three commands in their
 * own handler methods. These handler methods should be annotated
 * with <b>@CommandHandler<b/> annotation.
 *
 * The handler methods use <b>AggregateLifecycle.apply()<b/> method to register events.
 *
 * These events, in turn, are handled by methods annotated with <b>@EventSourcingHandler<b/>
 * annotation. Also, it is imperative that all state changes in an evert sourced aggregate
 * should be performed in these methods.
 *
 * Another important point to keep in mind is that the Aggregate identifier must be set
 * in the first method annotated with <b>@EventSourcingHandler<b/>. This will be the creation
 * Event.
 */
@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id;

    private double accountBalance;
    private String currency;
    private String status;

    /**
     * This constructor without arguments is declared
     * because Axon framework needs it. Basically, using
     * this constructor, Axon created an empty instance of
     * the aggregate. Then, it applies the events. If this
     * constructor is not present, it will result in an
     * exception.
     */
    public AccountAggregate() {

    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        AggregateLifecycle.apply(new AccountCreatedEvent(createAccountCommand.id,
                                                         createAccountCommand.accountBalance,
                                                         createAccountCommand.currency));
    }

    /**
     * The creation Event.
     * @param accountCreatedEvent
     */
    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent) {
        this.id = accountCreatedEvent.id;
        this.accountBalance = accountCreatedEvent.accountBalance;
        this.currency = accountCreatedEvent.currency;
        this.status = String.valueOf(Status.CREATED);

        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
    }

    /**
     * To handle the AccountActivatedEvent event.
     * @param accountActivatedEvent
     */
    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent) {
        this.status = String.valueOf(accountActivatedEvent.status);
    }

    /**
     * To register the MoneyCreditedEvent event.
     * @param creditMoneyCommand
     */
    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand) {
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.id,
                                        creditMoneyCommand.creditAmount,
                                        creditMoneyCommand.currency));
    }

    /**
     * To handle the MoneyCreditedEvent event.
     * @param moneyCreditedEvent
     */
    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent) {
        if (this.accountBalance < 0 &
                (this.accountBalance + moneyCreditedEvent.creditAmount) >=0) {
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
        }

        this.accountBalance += moneyCreditedEvent.creditAmount;
    }

    /**
     * To register the MoneyDebitedEvent event.
     * @param debitMoneyCommand
     */
    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand) {
        AggregateLifecycle.apply( new MoneyDebitedEvent(debitMoneyCommand.id,
                                        debitMoneyCommand.debitAmount,
                                        debitMoneyCommand.currency));
    }

    /**
     * To handle the MoneyDebitedEvent event.
     * @param moneyDebitedEvent
     */
    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent) {
        if (this.accountBalance < 0 &
                (this.accountBalance - moneyDebitedEvent.debitAmount) < 0) {
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.HOLD));
        }

        this.accountBalance -= moneyDebitedEvent.debitAmount;
    }

    /**
     * To handle the AccountHeldEvent event.
     * @param accountHeldEvent
     */
    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent) {
        this.status = String.valueOf(accountHeldEvent.status);
    }

}
