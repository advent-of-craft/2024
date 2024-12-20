export class Reindeer {
    constructor(
        public id: string,
        public name: string,
        public color: ReindeerColor
    ) {}
}

export enum ReindeerColor {
    White = 0,
    Black = 1,
    Purple = 2
}

export enum ReindeerErrorCode {
    NotFound,
    AlreadyExist
}

export class ReindeerResult {
    constructor(
        public id: string,
        public name: string,
        public color: ReindeerColor
    ) {}

    public static from(reindeer: Reindeer): ReindeerResult {
        return new ReindeerResult(reindeer.id, reindeer.name, reindeer.color);
    }
}