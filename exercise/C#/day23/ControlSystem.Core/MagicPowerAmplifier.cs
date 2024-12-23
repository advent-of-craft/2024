namespace ControlSystem.Core;

public class MagicPowerAmplifier(AmplifierType amplifierType)
{
    public float Amplify(float magicPower)
    {
        if (magicPower > 0)
            return magicPower * amplifierType.GetMultiplier();
        return magicPower;
    }
}