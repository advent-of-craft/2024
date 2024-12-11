namespace Delivery
{
    using Instruction = char;
    using FloorStrategy = Func<char, int>;

    public static class Building
    {
        private const Instruction Up = '(';
        private const Instruction Down = ')';
        private const string ElfSymbol = "🧝";

        private static readonly FloorStrategy Standard = c => c == Up ? 1 : -1;
        private static readonly FloorStrategy Elf = c => c switch
        {
            Down => 3,
            Up => -2,
            _ => 0
        };

        public static int WhichFloor(string instructions)
            => WhichFloor(
                instructions,
                instructions.Contains(ElfSymbol) ? Elf : Standard
            );

        private static int WhichFloor(string instructions, FloorStrategy strategy)
            => instructions
                .Select(strategy)
                .Sum();
    }
}