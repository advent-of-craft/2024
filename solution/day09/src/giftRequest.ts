export class GiftRequest {
    public readonly giftName: string;
    public readonly isFeasible: boolean;
    public readonly priority: Priority;

    constructor(giftName: string, isFeasible: boolean, priority: Priority) {
        this.giftName = giftName;
        this.isFeasible = isFeasible;
        this.priority = priority;
    }
}