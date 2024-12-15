namespace SantaChristmasList.Operations.Test;

public class BusinessTest
{
    private const string BarCode = "123";

    private readonly Factory _factory = new();
    private readonly Inventory _inventory = new();
    private readonly WishList _wishList = new();

    private readonly Child _john = new("John");
    private readonly Gift _toy = new("Toy");
    private readonly ManufacturedGift _manufacturedGift = new(BarCode);

    [Fact]
    public void Gift_ShouldBeLoadedIntoSleigh()
    {
        _wishList.Add(_john, _toy);
        _factory.Add(_toy, _manufacturedGift);
        _inventory.Add(BarCode, _toy);

        var sut = new Business(_factory, _inventory, _wishList);
        var sleigh = sut.LoadGiftsInSleigh(_john);

        sleigh[_john].Should().Be("Gift: Toy has been loaded!");
    }

    [Fact]
    public void Gift_ShouldNotBeLoaded_GivenChildIsNotOnWishList()
    {
        var sut = new Business(_factory, _inventory, _wishList);
        var sleigh = sut.LoadGiftsInSleigh(_john);

        sleigh.ContainsKey(_john).Should().BeFalse();
    }

    [Fact]
    public void Gift_ShouldNotBeLoaded_GivenToyWasNotManufactured()
    {
        _wishList.Add(_john, _toy);
        var sut = new Business(_factory, _inventory, _wishList);
        var sleigh = sut.LoadGiftsInSleigh(_john);

        sleigh.ContainsKey(_john).Should().BeFalse();
    }

    [Fact]
    public void Gift_ShouldNotBeLoaded_GivenToyWasMisplaced()
    {
        _wishList.Add(_john, _toy);
        _factory.Add(_toy, _manufacturedGift);
        var sut = new Business(_factory, _inventory, _wishList);
        var sleigh = sut.LoadGiftsInSleigh(_john);

        sleigh.ContainsKey(_john).Should().BeFalse();
    }
}