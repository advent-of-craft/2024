package domain;

import domain.core.Repository;
import io.vavr.control.Option;

public interface ToyRepository extends Repository<Toy> {
    Option<Toy> findByName(String toyName);
}