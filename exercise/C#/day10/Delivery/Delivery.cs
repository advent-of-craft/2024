namespace Delivery
{
    public static class Building
    {
        public static int WhichFloor(string instructions)
        {
            List<Tuple<char, int>> val = [];

            for (int i = 0; i < instructions.Length; i++)
            {
                var c = instructions[i];

                if (instructions.Contains("🧝"))
                {
                    int j;
                    if (c == ')') j = 3;
                    else j = -2;

                    val.Add(new Tuple<char, int>(c, j));
                }
                else if (!instructions.Contains("🧝"))
                {
                    val.Add(new Tuple<char, int>(c, c == '(' ? 1 : -1));
                }
                else val.Add(new Tuple<char, int>(c, c == '(' ? 42 : -2));
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