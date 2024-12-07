package production

import production.Status.*

enum Status {
  case Producing, Produced
}

case class Gift(name: String, var status: Status = Status.Producing)

class Workshop {
  private val gifts = scala.collection.mutable.ListBuffer[Gift]()

  def addGift(gift: Gift): Unit = {
    gifts += gift
  }

  def completeGift(name: String): Option[Gift] = {
    gifts.find(_.name == name).map { gift =>
      gift.copy(status = Produced)
    }
  }
}