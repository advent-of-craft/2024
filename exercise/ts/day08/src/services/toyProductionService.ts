import {ToyRepository} from "../domain/toyRepository";
import {Toy} from "../domain/toy";

export class ToyProductionService {
    private repository: ToyRepository;

    constructor(repository: ToyRepository) {
        this.repository = repository;
    }

    assignToyToElf(toyName: string): void {
        const toy = this.repository.findByName(toyName);
        if (toy && toy.getState() === Toy.State.UNASSIGNED) {
            toy.setState(Toy.State.IN_PRODUCTION);
            this.repository.save(toy);
        }
    }
}