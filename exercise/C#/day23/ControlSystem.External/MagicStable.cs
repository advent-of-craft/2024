namespace ControlSystem.External
{
    public class MagicStable
    {
        // Dasher is sick since yesterday
        private readonly Reindeer _dasher = new("Dasher", 4, 10, true);
        private readonly Reindeer _dancer = new("Dancer", 2, 8);
        private readonly Reindeer _prancer = new("Prancer", 3, 9);

        private readonly Reindeer _vixen = new("Vixen", 3, 6);

        // Comet is sick as well
        private readonly Reindeer _comet = new("Comet", 4, 9, true);
        private readonly Reindeer _cupid = new("Cupid", 4, 6);
        private readonly Reindeer _donner = new("Donner", 7, 6);
        private readonly Reindeer _blitzen = new("Blitzen", 8, 7);
        private readonly Reindeer _rudolph = new("Rudolph", 6, 3);

        public List<Reindeer> GetAllReindeers() =>
            [_dasher, _dancer, _prancer, _vixen, _comet, _cupid, _donner, _blitzen, _rudolph];
    }
}