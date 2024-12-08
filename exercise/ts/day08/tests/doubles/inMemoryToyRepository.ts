import {Toy} from "../../src/domain/toy";
import {ToyRepository} from "../../src/domain/toyRepository";

export class InMemoryToyRepository implements ToyRepository {
    private toys: Toy[] = [];

    findByName(name: string): Toy | null {
        return this.toys.find(t => t.getName() === name) || null;
    }

    save(toy: Toy): void {
        this.toys = this.toys.filter(t => t.getName() !== toy.getName());
        this.toys.push(toy);
    }
}