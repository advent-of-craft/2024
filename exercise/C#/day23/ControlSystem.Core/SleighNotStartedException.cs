namespace ControlSystem.Core
{
    public class SleighNotStartedException : Exception
    {
        public override string Message =>
            "The sleigh is not started. Please start the sleigh before any other action...";
    }
}