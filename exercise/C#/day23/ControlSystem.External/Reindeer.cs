namespace ControlSystem.External
{
    public class Reindeer
    {
        private int _spirit = 0;
        private int _age = 0;
        private readonly string _name;
        public bool Sick = false;
        public int TimesHarnessing = 0;
        private int _powerPullLimit = 0;

        public Reindeer(string name, int age, int spirit)
            : this(name, age, spirit, false)
        {
        }

        public Reindeer(string name, int age, int spirit, bool sick)
        {
            _name = name;
            _spirit = spirit;
            _age = age;
            Sick = sick;

            _powerPullLimit = age <= 5 ? 5 : 5 - (age - 5);
        }

        public float GetMagicPower()
        {
            if (!Sick || NeedsRest())
            {
                if (_age == 1)
                    return _spirit * 0.5f;
                else if (_age <= 5)
                    return _spirit;
                else
                    return _spirit * 0.25f;
            }
            else
            {
                return 0;
            }
        }

        public bool NeedsRest()
        {
            if (!Sick)
            {
                return TimesHarnessing == _powerPullLimit;
            }
            else
            {
                return true;
            }
        }
    }
}