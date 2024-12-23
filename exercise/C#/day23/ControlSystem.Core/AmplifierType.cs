namespace ControlSystem.Core
{
    public enum AmplifierType
    {
        Basic = 1,
        Blessed = 2,
        Divine = 3
    }

    public static class AmplifierTypeExtensions
    {
        public static int GetMultiplier(this AmplifierType amplifierType) => (int) amplifierType;
    }
}