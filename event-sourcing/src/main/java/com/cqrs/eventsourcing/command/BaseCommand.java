package com.cqrs.eventsourcing.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * This class represents a generic Command, to make
 * the id field flexible accross differente classes
 * that extend this class.
 *
 * The @TargetAggregateIdentifier annotation is an
 * Axon specific requirement to identify the aggregate
 * instance.
 * In other words, this annotation is required for Axon to
 * determine the instance of the Aggregate that should handle
 * the command.
 * The annotation can be placed on either the field or the getter
 * method.
 */
public class BaseCommand<T> {

    @TargetAggregateIdentifier
    public final T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
