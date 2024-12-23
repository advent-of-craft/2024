using ControlSystem.External;

namespace ControlSystem.Core
{
    public class ReindeerPowerUnit
    {
        public Reindeer Reindeer { get; }
        private readonly MagicPowerAmplifier _amplifier = new(AmplifierType.Basic);

        public ReindeerPowerUnit(Reindeer reindeer)
        {
            Reindeer = reindeer;
        }

        public float HarnessMagicPower()
        {
            if (!Reindeer.NeedsRest())
            {
                Reindeer.TimesHarnessing++;
                return _amplifier.Amplify(Reindeer.GetMagicPower());
            }

            return 0;
        }

        public float CheckMagicPower()
        {
            return Reindeer.GetMagicPower();
        }
    }
}