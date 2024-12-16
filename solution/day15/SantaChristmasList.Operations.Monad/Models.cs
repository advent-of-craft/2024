using LanguageExt;

namespace SantaChristmasList.Operations.Monad;

public class Sleigh(IDictionary<Child, Either<NonDeliverableGift, Gift>> dictionary)
    : Dictionary<Child, Either<NonDeliverableGift, Gift>>(dictionary);

public record Gift(string Name);

public record ManufacturedGift(string BarCode);

public record Child(string Name);

public record NonDeliverableGift(string Reason);