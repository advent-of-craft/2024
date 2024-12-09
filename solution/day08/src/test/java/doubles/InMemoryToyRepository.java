package doubles;

import domain.Toy;
import domain.ToyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryToyRepository implements ToyRepository {
    private final List<Toy> toys = new ArrayList<>();

    @Override
    public Optional<Toy> findByName(String name) {
        return toys.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst();
    }

    public Toy getUnsafe(String name) {
        return findByName(name)
                .orElseThrow();
    }

    @Override
    public void save(Toy toy) {
        toys.remove(toy);
        toys.add(toy);
    }
}