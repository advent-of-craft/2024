export class Toy {
    static State = {
        UNASSIGNED: 'UNASSIGNED',
        IN_PRODUCTION: 'IN_PRODUCTION',
        COMPLETED: 'COMPLETED'
    };

    private readonly name: string;
    private state: string;

    constructor(name: string, state: string) {
        this.name = name;
        this.state = state;
    }

    getName(): string {
        return this.name;
    }

    getState(): string {
        return this.state;
    }

    setState(state: string): void {
        this.state = state;
    }
}