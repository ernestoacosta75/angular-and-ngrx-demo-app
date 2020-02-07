package com.cqrs.eventsourcing.service.event;

import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * IN this service implementation we're hiring the
 * Axon <b>EventStore<b/>.
 *
 * Basically, <b>EventStore<b/> provides a method
 * to read events for a particular AggregatedId.
 * In other words, we call the <b>readEvents()<b/>
 * method with the AggregatedId (or Account#) as input.
 * Then, we collect the output stream and transform it
 * to a list.
 */
@AllArgsConstructor(staticName = "of")
public class AccountQueryServiceImpl implements AccountQueryService {

    private final EventStore eventStore;

    @Override
    public List<Object> listEventsForAccount(String accountNumber) {
        return eventStore.readEvents(accountNumber)
                .asStream()
                .map(s -> s.getPayload())
                .collect(Collectors.toList());
    }
}
