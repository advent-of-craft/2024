using static System.Linq.Enumerable;

namespace Travel
{
    public static class SantaTravelCalculator
    {
        public static int CalculateTotalDistanceRecursively(int numberOfReindeers)
        {
            if (numberOfReindeers == 1) return 1;

            checked
            {
                return 2 * CalculateTotalDistanceRecursively(numberOfReindeers - 1) + 1;
            }
        }

        public static long CalculateTotalDistance(int numberOfReindeers)
        {
            var distanceToNextReindeer = 1L;
            var distance = 0L;
            var remainingReindeersToVisit = numberOfReindeers;

            while (remainingReindeersToVisit > 0)
            {
                distance += distanceToNextReindeer;
                distanceToNextReindeer *= 2;
                remainingReindeersToVisit--;
            }

            return distance;
        }

        public static long CalculateTotalDistanceWithLinQ(int numberOfReindeers)
            => Range(1, numberOfReindeers)
                .Aggregate((totalDistance: 0L, distanceToNextReindeer: 1L),
                    (acc, _) => (acc.totalDistance + acc.distanceToNextReindeer, acc.distanceToNextReindeer * 2),
                    acc => acc.totalDistance);
        
        public static ulong CalculateTotalDistanceWithBitWise(int numberOfReindeers) => (1UL << numberOfReindeers) - 1;
    }
}