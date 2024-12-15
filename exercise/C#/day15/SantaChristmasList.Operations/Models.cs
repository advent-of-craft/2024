namespace SantaChristmasList.Operations;

public class Sleigh : Dictionary<Child, string>;
public record Gift(string Name);
public record ManufacturedGift(string BarCode);
public record Child(string Name);