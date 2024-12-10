namespace Delivery
{
    public static class Building
    {
        public static int WhichFloor(string instructions)
        {
            List<Tuple<char, int>> val = [];
            var hasElf = instructions.Contains("🧝");
            
            
            for (int i = 0; i < instructions.Length; i++)
            {
                int increment;
                var currentChar = instructions[i];
                if(currentChar != '(' && currentChar != ')') continue;
                if (hasElf)
                {
                    increment=  currentChar == ')' ? 3 : -2;
                }
                else
                {
                    increment = currentChar == '(' ? 1 : -1;
                }
                val.Add(new Tuple<char, int>(currentChar, increment));
            }

            return val.Sum(kp => kp.Item2);
        }
    }
}