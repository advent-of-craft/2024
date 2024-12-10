import {Child} from "./child";

export class SantaService {
    public evaluateRequest(child: Child): boolean {
        return child.behavior === "nice" && child.giftRequest.isFeasible;
    }
}