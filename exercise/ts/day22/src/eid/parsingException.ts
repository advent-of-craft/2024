export class ParsingException extends Error {
    constructor(message: string) {
        super(message);
        this.name = 'ParsingException';
    }
}