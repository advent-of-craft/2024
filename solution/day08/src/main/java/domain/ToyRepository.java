package domain;

import java.util.Optional;

public interface ToyRepository {
    Optional<Toy> findByName(String name);
    void save(Toy toy);
}
