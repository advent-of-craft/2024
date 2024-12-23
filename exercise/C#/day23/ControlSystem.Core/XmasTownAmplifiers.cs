namespace ControlSystem.Core;

public class XmasTownAmplifiers
{
    public static XmasTownAmplifiers Build()
    {
        var amplifiers = new XmasTownAmplifiers();
        amplifiers._amplifiers.Push(new MagicPowerAmplifier(AmplifierType.Blessed));
        amplifiers._amplifiers.Push(new MagicPowerAmplifier(AmplifierType.Blessed));
        amplifiers._amplifiers.Push(new MagicPowerAmplifier(AmplifierType.Divine));
        return amplifiers;
    }

    private readonly Stack<MagicPowerAmplifier> _amplifiers = new();
    public MagicPowerAmplifier GetNext() => _amplifiers.Count != 0 
        ? _amplifiers.Pop() 
        : new MagicPowerAmplifier(AmplifierType.Basic);
}