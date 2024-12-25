using System.Diagnostics;

namespace TimeCapsule;

public static class Browser
{
    public static void Open(string filePath)
        => new Process
        {
            StartInfo = new ProcessStartInfo
            {
                FileName = filePath,
                UseShellExecute = true
            }
        }.Start();
}