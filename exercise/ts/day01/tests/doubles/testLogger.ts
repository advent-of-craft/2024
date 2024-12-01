import {Logger} from "../../src/logger";

export class TestLogger implements Logger {
    private message: string;

    public log(message: string): void {
        this.message = message;
    }

    public getLog(): string {
        return this.message;
    }
}