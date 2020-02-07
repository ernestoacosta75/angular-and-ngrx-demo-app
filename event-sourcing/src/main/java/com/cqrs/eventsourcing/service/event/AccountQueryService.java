package com.cqrs.eventsourcing.service.event;

import java.util.List;

/**
 * This interface will be used to handle the Events.
 *
 * To be implemented.
 */
public interface AccountQueryService {
    public List<Object> listEventsForAccount(String accountNumber);
}
