export class DomainError {
    constructor(public message: string) {
    }

    static anError(message: string): DomainError {
        return new DomainError(message);
    }
}