import {Status} from "./status";

export class Gift {
    private readonly name: string;
    private readonly status: Status;

    constructor(name: string, status: Status = Status.Producing) {
        this.name = name;
        this.status = status;
    }

    getName(): string {
        return this.name;
    }

    getStatus(): Status {
        return this.status;
    }

    withStatus(status: Status): Gift {
        return new Gift(this.name, status);
    }
}