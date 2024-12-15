package santaChristmasList.operations

case class Gift(name: String)

case class ManufacturedGift(barCode: String)

case class Child(name: String)

class Sleigh extends collection.mutable.HashMap[Child, String]
