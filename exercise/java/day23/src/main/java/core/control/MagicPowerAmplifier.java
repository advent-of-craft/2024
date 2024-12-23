package core.control;

public class MagicPowerAmplifier {
    private AmplifierType amplifierType;

    public MagicPowerAmplifier(AmplifierType amplifierType) {
        this.amplifierType = amplifierType;
    }

    public float amplify(float magicPower) {
        if (magicPower > 0)
            return magicPower * amplifierType.getMultiplier();
        return magicPower;
    }
}
