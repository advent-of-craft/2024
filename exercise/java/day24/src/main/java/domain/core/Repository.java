package domain.core;

import io.vavr.control.Option;

import java.util.UUID;

public interface Repository<A extends EventSourcedAggregate> {
    Option<A> findById(UUID id);

    void save(A aggregate);
}
