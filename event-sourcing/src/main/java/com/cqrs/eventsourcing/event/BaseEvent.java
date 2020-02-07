package com.cqrs.eventsourcing.event;

/**
 * This class represents a generic Command, to make
 * the id field flexible accross differente classes
 * that extend this class.
 */
public class BaseEvent<T> {

    public final T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
