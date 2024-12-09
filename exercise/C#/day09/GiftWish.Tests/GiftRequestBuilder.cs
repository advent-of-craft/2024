namespace GiftWish.Tests;

public class GiftRequestBuilder
{
    private string _giftName = "Bicycle";
    private bool _isFeasible = true;
    private Priority _priority = Priority.NiceToHave;

    public static GiftRequestBuilder AGiftRequest() => new();
    
    public static GiftRequestBuilder AFeasibleGift() => AGiftRequest().WithFeasibility(true);
    
    public static GiftRequestBuilder AnInfeasibleGift() => AGiftRequest().WithFeasibility(false);

    public GiftRequestBuilder WithGiftName(string giftName)
    {
        _giftName = giftName;
        return this;
    }

    public GiftRequestBuilder WithFeasibility(bool isFeasible)
    {
        _isFeasible = isFeasible;
        return this;
    }

    public GiftRequestBuilder WithPriority(Priority priority)
    {
        _priority = priority;
        return this;
    }
    
    public static implicit operator GiftRequest(GiftRequestBuilder builder) => builder.Build();

    public GiftRequest Build() => new(_giftName, _isFeasible, _priority);
}