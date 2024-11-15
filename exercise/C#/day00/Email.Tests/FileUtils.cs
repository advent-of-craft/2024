using System.Reflection;
using static System.IO.Path;

namespace Email.Tests;

public static class FileUtils
{
    public static string LoadFile(string fileName)
        => File.ReadAllText(
            Combine(
                GetDirectoryName(Assembly.GetExecutingAssembly().Location)!,
                fileName)
        );
}