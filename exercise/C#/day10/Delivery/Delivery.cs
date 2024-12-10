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
                var c = instructions[i];
                
                if (hasElf)
                {
                    int j;
                    if (c == ')') j = 3;
                    else j = -2;

                    val.Add(new Tuple<char, int>(c, j));
                }
                else
                {
                    val.Add(new Tuple<char, int>(c, c == '(' ? 1 : -1));
                }
            }

            int result = 0;
            foreach (var kp in val)
            {
                result += kp.Item2;
            }

            return result;
        }
    }
}