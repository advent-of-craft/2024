package core.control.generator;

public class MagicPowerAmplifier {
    private final AmplifierType amplifierType;

    public MagicPowerAmplifier(AmplifierType amplifierType) {
        this.amplifierType = amplifierType;
    }

    public static MagicPowerAmplifier divineAmplifier() {
        return new MagicPowerAmplifier(AmplifierType.DIVINE);
    }

    public static MagicPowerAmplifier blessedAmplifier() {
        return new MagicPowerAmplifier(AmplifierType.BLESSED);
    }

    public static MagicPowerAmplifier basicAmplifier() {
        return new MagicPowerAmplifier(AmplifierType.BASIC);
    }

    public float amplify(float magicPower) {
        return magicPower > 0
                ? magicPower * amplifierType.getMultiplier()
                : magicPower;
    }
}
