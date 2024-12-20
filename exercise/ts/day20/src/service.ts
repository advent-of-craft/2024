import {v4 as uuidv4} from 'uuid';
import {Either, left, right} from 'fp-ts/lib/Either';
import {Reindeer, ReindeerColor, ReindeerErrorCode} from './types';

export class ReindeerService {
    private reindeer: Reindeer[] = [
        new Reindeer("40f9d24d-d3e0-4596-adc5-b4936ff84b19", 'Petar', ReindeerColor.Black)
    ];

    public get(id: string): Either<ReindeerErrorCode, Reindeer> {
        const reindeer = this.reindeer.find(r => r.id === id);
        return reindeer ? right(reindeer) : left(ReindeerErrorCode.NotFound);
    }

    public create(reindeerToCreate: ReindeerToCreate): Either<ReindeerErrorCode, Reindeer> {
        const existingReindeer = this.reindeer.find(r => r.name === reindeerToCreate.name);
        if (existingReindeer) return left(ReindeerErrorCode.AlreadyExist);

        const newReindeer = new Reindeer(uuidv4(), reindeerToCreate.name, reindeerToCreate.color);
        this.reindeer.push(newReindeer);

        return right(newReindeer);
    }
}

export class ReindeerToCreate {
    constructor(
        public name: string,
        public color: ReindeerColor
    ) {
    }
}