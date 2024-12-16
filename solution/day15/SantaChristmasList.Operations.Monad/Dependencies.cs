using LanguageExt;

namespace SantaChristmasList.Operations.Monad;

public class Factory : Dictionary<Gift, ManufacturedGift>
{
    public Either<NonDeliverableGift, ManufacturedGift> FindManufacturedGift(Gift gift)
        => ContainsKey(gift) ? this[gift] : new NonDeliverableGift("Gift wasn't manufactured!");
}

public class Inventory : Dictionary<string, Gift>
{
    public Either<NonDeliverableGift, Gift> PickUpGift(string barCode)
        => ContainsKey(barCode)
            ? this[barCode]
            : new NonDeliverableGift("The gift has probably been misplaced by the elves!");
}

public class WishList : Dictionary<Child, Gift>
{
    public Either<NonDeliverableGift, Gift> IdentifyGift(Child child)
        => ContainsKey(child) ? this[child] : new NonDeliverableGift("Child wasn't nice this year!");
}