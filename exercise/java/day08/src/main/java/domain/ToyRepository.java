package domain;

public interface ToyRepository {
    Toy findByName(String name);
    void save(Toy toy);
}
