namespace Workshop
{
    public enum Status
    {
        Producing,
        Produced
    }

    public record Gift(string Name, Status Status = Status.Producing);

    public class Workshop
    {
        private readonly List<Gift> _gifts = [];

        public void AddGift(Gift gift) => _gifts.Add(gift);

        public Gift? CompleteGift(string name)
        {
            var gift = _gifts.FirstOrDefault(g => g.Name == name);
            return gift != null
                ? gift with {Status = Status.Produced}
                : null;
        }
    }
}