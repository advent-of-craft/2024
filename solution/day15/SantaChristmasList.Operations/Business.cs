namespace SantaChristmasList.Operations;

public class Business(Factory factory, Inventory inventory, WishList wishList)
{
    public Sleigh LoadGiftsInSleigh(params Child[] children)
    {
        var sleigh = new Sleigh();
        foreach (var child in children)
        {
            var message = LoadGiftForChild(child);
            sleigh.Add(child, message);
        }

        return sleigh;
    }

    private string LoadGiftForChild(Child child)
    {
        var gift = wishList.IdentifyGift(child);
        if (gift is null)
        {
            return "Missing gift: Child wasn't nice this year!";
        }

        var manufactured = factory.FindManufacturedGift(gift);
        if (manufactured is null)
        {
            return "Missing gift: Gift wasn't manufactured!";
        }

        var finalGift = inventory.PickUpGift(manufactured.BarCode);
        return finalGift is null
            ? "Missing gift: The gift has probably been misplaced by the elves!"
            : $"Gift: {finalGift.Name} has been loaded!";
    }
}