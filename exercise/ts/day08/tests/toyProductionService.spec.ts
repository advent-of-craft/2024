import {InMemoryToyRepository} from "./doubles/inMemoryToyRepository";
import {ToyProductionService} from "../src/services/toyProductionService";
import {Toy} from "../src/domain/toy";

describe('ToyProductionService', () => {
    const TOY_NAME = 'Train';

    it('assignToyToElfShouldPassTheItemInProduction', () => {
        const repository = new InMemoryToyRepository();
        const service = new ToyProductionService(repository);
        repository.save(new Toy(TOY_NAME, Toy.State.UNASSIGNED));

        service.assignToyToElf(TOY_NAME);

        const toy = repository.findByName(TOY_NAME);
        expect(toy?.getState()).toBe(Toy.State.IN_PRODUCTION);
    });
});
