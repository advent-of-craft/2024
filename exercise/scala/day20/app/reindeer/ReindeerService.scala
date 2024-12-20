package reindeer

import java.util.UUID
import scala.collection.mutable

class ReindeerService {
  private var reindeer: mutable.Seq[Reindeer] = mutable.Seq(
    Reindeer(UUID.fromString("40F9D24D-D3E0-4596-ADC5-B4936FF84B19"), "Petar", ReindeerColor.Black)
  )

  def get(id: UUID): Either[ReindeerErrorCode.Value, Reindeer] =
    reindeer.find(_.id == id).toRight(ReindeerErrorCode.NotFound)

  def create(reindeerToCreate: ReindeerToCreate): Either[ReindeerErrorCode.Value, Reindeer] =
    reindeer.find(_.name == reindeerToCreate.name)
      .map(_ => ReindeerErrorCode.AlreadyExist)
      .toLeft(createAndAddReindeer(reindeerToCreate))

  private def createAndAddReindeer(reindeerToCreate: ReindeerToCreate): Reindeer = {
    val newReindeer = Reindeer(UUID.randomUUID(), reindeerToCreate.name, reindeerToCreate.color)
    reindeer = reindeer :+ newReindeer

    newReindeer
  }
}

case class Reindeer(id: UUID, name: String, color: ReindeerColor.Value)

object ReindeerColor extends Enumeration {
  val White, Black, Purple = Value
}

object ReindeerErrorCode extends Enumeration {
  val NotFound, AlreadyExist = Value
}

case class ReindeerResult(id: UUID, name: String, color: ReindeerColor.Value)

object ReindeerResult {
  def from(reindeer: Reindeer): ReindeerResult =
    ReindeerResult(reindeer.id, reindeer.name, reindeer.color)
}

case class ReindeerToCreate(id: UUID, name: String, color: ReindeerColor.Value)

case class ReindeerToCreateRequest(name: String, color: ReindeerColor.Value) {
  def toDomain: ReindeerToCreate = ReindeerToCreate(UUID.randomUUID(), name, color)
}