namespace ControlSystem.Core
{
    public class ReindeersNeedRestException : Exception
    {
        public override string Message => "The reindeer needs rest. Please park the sleigh...";
    }
}