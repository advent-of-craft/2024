import {Child} from "../src/child";
import {GiftRequest} from "../src/giftRequest";

export class ChildBuilder {
    private behavior: Behavior;
    private isFeasible: boolean = true;

    static aChild(): ChildBuilder {
        return new ChildBuilder();
    }

    nice(): ChildBuilder {
        this.behavior = "nice";
        return this;
    }

    naughty(): ChildBuilder {
        this.behavior = "naughty";
        return this;
    }

    requestingInfeasibleGift(): ChildBuilder {
        this.isFeasible = false;
        return this;
    }

    build(): Child {
        return new Child("Jane",
            "Doe",
            9,
            this.behavior,
            new GiftRequest("Any gift", this.isFeasible, "nice to have")
        );
    }
}