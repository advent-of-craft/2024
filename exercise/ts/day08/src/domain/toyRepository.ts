import {Toy} from "./toy";

export interface ToyRepository {
    findByName(name: string): Toy | null;
    save(toy: Toy): void;
}