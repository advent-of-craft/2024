namespace GiftWish.Tests;

public class ChildBuilder
{
    private int _age = 9;
    private Behavior _behavior = Behavior.Nice;
    private string _firstName = "Alice";
    private GiftRequest _giftRequest = GiftRequestBuilder.AGiftRequest().Build();
    private string _lastName = "Thomas";

    public static ChildBuilder AChild() => new();

    public ChildBuilder WithFirstName(string firstName)
    {
        _firstName = firstName;
        return this;
    }

    public ChildBuilder WithLastName(string lastName)
    {
        _lastName = lastName;
        return this;
    }

    public ChildBuilder WithAge(int age)
    {
        _age = age;
        return this;
    }

    public ChildBuilder WithBehavior(Behavior behavior)
    {
        _behavior = behavior;
        return this;
    }

    public ChildBuilder WithGiftRequest(GiftRequest giftRequest)
    {
        _giftRequest = giftRequest;
        return this;
    }

    public Child Build() => new(_firstName, _lastName, _age, _behavior, _giftRequest);
}