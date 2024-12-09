package service;

import domain.ToyRepository;

public class ToyProductionService {
    private final ToyRepository repository;

    public ToyProductionService(ToyRepository repository) {
        this.repository = repository;
    }

    public void assignToyToElf(String toyName) {
        repository.findByName(toyName)
                .ifPresent(toy -> {
                    toy.assignToElf();
                    repository.save(toy);
                });
    }
}