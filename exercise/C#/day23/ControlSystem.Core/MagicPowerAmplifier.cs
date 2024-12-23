namespace ControlSystem.Core
{
    public class MagicPowerAmplifier
    {
        private readonly AmplifierType _amplifierType;

        public MagicPowerAmplifier(AmplifierType amplifierType) => _amplifierType = amplifierType;

        public float Amplify(float magicPower)
        {
            if (magicPower > 0)
                return magicPower * _amplifierType.GetMultiplier();
            return magicPower;
        }
    }
}