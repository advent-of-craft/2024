using ControlSystem.External;

namespace ControlSystem.Core;

public class ReindeerPowerUnit
{
    public Reindeer Reindeer { get; }
    private readonly MagicPowerAmplifier _amplifier;

    public ReindeerPowerUnit(Reindeer reindeer, MagicPowerAmplifier magicPowerAmplifier)
    {
        Reindeer = reindeer;
        _amplifier = magicPowerAmplifier;
    }
    
    public float HarnessMagicPower()
    {
        if (Reindeer.NeedsRest()) return 0;
        
        Reindeer.TimesHarnessing++;
        return _amplifier.Amplify(Reindeer.GetMagicPower());
    }

    public float CheckMagicPower()
    {
        return Reindeer.GetMagicPower();
    }

    public void ResetHarnessing() => Reindeer.TimesHarnessing = 0;
}