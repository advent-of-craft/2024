namespace Delivery
{
    /** TODOs 
     * - extract multiplicand to parameters and merge CalculateFloorIncrement and CalculateFloorIncrementWithElf
     * - create configuration depending on the presence of an elf
     */
    public static class Building
    {
        public static int WhichFloor(string instructions)
        {
            List<Tuple<char, int>> val = [];

            if (instructions.Contains("🧝"))
            {
                instructions = instructions.Replace("🧝", "");
                val = CalculateFloorIncrementWithElf(instructions);
            } else {
                val = CalculateFloorIncrement(instructions);
            }

            return val.Sum(kp => kp.Item2);
        }

        private static List<Tuple<char, int>> CalculateFloorIncrement(string instructions)
        {
            List<Tuple<char, int>> val = [];
            val.Add(new Tuple<char, int>('(', instructions.Count(c => c == '(')));
            val.Add(new Tuple<char, int>(')', -instructions.Count(c => c == ')')));

            return val;
        }
        
        private static List<Tuple<char, int>> CalculateFloorIncrementWithElf(string instructions)
        {
            List<Tuple<char, int>> val = [];
            val.Add(new Tuple<char, int>('(', -2 * instructions.Count(c => c == '(')));
            val.Add(new Tuple<char, int>(')', 3 * instructions.Count(c => c == ')')));

            return val;
        }
    }
}