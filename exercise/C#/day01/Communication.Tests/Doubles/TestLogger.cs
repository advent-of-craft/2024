namespace Communication.Tests.Doubles
{
    public class TestLogger : ILogger
    {
        private string? _message;
        public void Log(string? message) => _message = message;
        public string? LoggedMessage() => _message;
    }
}