import {Option} from "fp-ts/Option";
import {Toy} from "./Toy";

export interface IToyRepository {
    findByName(toyName: string): Option<Toy>;

    findById(id: string): Option<Object>;

    save(toy: Toy): void;
}