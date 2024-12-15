namespace SantaChristmasList.Operations;

public class Business(Factory factory, Inventory inventory, WishList wishList)
{
    public Sleigh LoadGiftsInSleigh(params Child[] children)
    {
        var list = new Sleigh();
        foreach (var child in children)
        {
            var gift = wishList.IdentifyGift(child);
            if (gift is not null)
            {
                var manufactured = factory.FindManufacturedGift(gift);
                if (manufactured is not null)
                {
                    var finalGift = inventory.PickUpGift(manufactured.BarCode);
                    if (finalGift is not null)
                    {
                        list.Add(child, $"Gift: {finalGift.Name} has been loaded!");
                    }
                }
            }
        }
        return list;
    }
}