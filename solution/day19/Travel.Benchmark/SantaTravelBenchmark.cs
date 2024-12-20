using BenchmarkDotNet.Attributes;
using static Travel.SantaTravelCalculator;

namespace Travel.Benchmark
{
    public class SantaTravelBenchmark
    {
        [Params(1, 5, 10, 20, 30)] public int NumberOfReindeers;

        [Benchmark]
        public void Recursive()
            => CalculateTotalDistanceRecursively(NumberOfReindeers);

        [Benchmark]
        public void While()
            => CalculateTotalDistance(NumberOfReindeers);

        [Benchmark]
        public void LinQ()
            => CalculateTotalDistanceWithLinQ(NumberOfReindeers);
        
        [Benchmark]
        public void BitWise()
            => CalculateTotalDistanceWithBitWise(NumberOfReindeers);
    }
}