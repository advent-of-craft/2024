namespace Delivery
{
    public static class Building
    {
        public static int WhichFloor(string instructions)
        {
            List<Tuple<char, int>> val = [];
            var hasElf = instructions.Contains("🧝");

            if (hasElf)
            {
                instructions = instructions.Replace("🧝", "");
                CalculateFloorIncrementWithElf(instructions, val);
            } else {
                CalculateFloorIncrement(instructions, val);
            }
            

            return val.Sum(kp => kp.Item2);
        }

        private static void CalculateFloorIncrement(string instructions, List<Tuple<char, int>> val)
        {
            for (int i = 0; i < instructions.Length; i++)
            {
                var currentChar = instructions[i];
                var increment = currentChar == '(' ? 1 : -1;
                val.Add(new Tuple<char, int>(currentChar, increment));
            }
        }
        
        private static void CalculateFloorIncrementWithElf(string instructions, List<Tuple<char, int>> val)
        {
            for (int i = 0; i < instructions.Length; i++)
            {
                var currentChar = instructions[i];
                var increment=  currentChar == ')' ? 3 : -2;
                val.Add(new Tuple<char, int>(currentChar, increment));
            }
        }
    }
}