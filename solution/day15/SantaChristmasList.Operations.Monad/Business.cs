using LanguageExt;

namespace SantaChristmasList.Operations.Monad;

public class Business(Factory factory, Inventory inventory, WishList wishList)
{
    public Sleigh LoadGiftsInSleigh(params Child[] children) =>
        new(children.ToDictionary(c => c, LoadGiftForChildWithEither));

    private Either<NonDeliverableGift, Gift> LoadGiftForChildWithEither(Child child)
        => wishList.IdentifyGift(child)
            .Bind(factory.FindManufacturedGift)
            .Bind(gift => inventory.PickUpGift(gift.BarCode));
}