export class ReindeersNeedRestException extends Error {
    constructor() {
        super("The reindeer needs rest. Please park the sleigh...");
    }
}

export class SleighNotStartedException extends Error {
    constructor() {
        super("The sleigh is not started. Please start the sleigh before any other action...");
    }
}