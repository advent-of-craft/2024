using static System.IO.File;

namespace TimeCapsule;

public class Capsule
{
    private const string Template = "timecapsule_template.html";
    public const string FilePath = "timecapsule.html";

    public string? PastMessage { get; private set; }
    public DateTime? Timestamp { get; private set; }
    public bool HasPastMessage { get; private set; }

    public Capsule() => HasPastMessage = LoadPastMessage();

    private bool LoadPastMessage()
    {
        if (!Exists(FilePath))
            return false;

        var htmlContent = ReadAllText(FilePath);
        var startIndex = htmlContent.IndexOf("<!--MESSAGE_START-->", StringComparison.Ordinal);
        var endIndex = htmlContent.IndexOf("<!--MESSAGE_END-->", StringComparison.Ordinal);

        if (startIndex == -1 || endIndex == -1) return false;

        PastMessage = htmlContent.Substring(startIndex + 18, endIndex - (startIndex + 18)).Trim();
        Timestamp = GetLastWriteTime(FilePath);

        return true;
    }

    public void SaveMessage(string message)
    {
        if (!Exists(Template))
            throw new FileNotFoundException("HTML template file not found!");

        var filledContent = ReadAllText(Template)
            .Replace("{{message}}", message)
            .Replace("{{timestamp}}", DateTime.Now.ToString("MMMM dd, yyyy HH:mm:ss"));

        WriteAllText(FilePath, filledContent);

        Timestamp = DateTime.Now;
        PastMessage = message;
    }
}