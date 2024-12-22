using System.Text.RegularExpressions;
using LanguageExt;
using static LanguageExt.Option<int>;

namespace EID
{
    public static partial class StringExtensions
    {
        public static Option<int> ToInt(this string potentialNumber)
            => IsANumber(potentialNumber)
                ? int.Parse(potentialNumber)
                : None;

        private static bool IsANumber(string str) => NumberRegex().Match(str).Success;

        [GeneratedRegex("[0-9.]+")]
        private static partial Regex NumberRegex();
    }
}