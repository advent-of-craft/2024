namespace GiftWish
{
    public class SantaService
    {
        public bool EvaluateRequest(Child child) => child is {Behavior: Behavior.Nice, GiftRequest.IsFeasible: true};
    }
}