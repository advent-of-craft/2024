import {AmplifierType} from "./amplifierType";

export class MagicPowerAmplifier {
    private amplifierType: AmplifierType;

    constructor(amplifierType: AmplifierType) {
        this.amplifierType = amplifierType;
    }

    public amplify(magicPower: number): number {
        return magicPower > 0 ? magicPower * this.amplifierType : magicPower;
    }
}