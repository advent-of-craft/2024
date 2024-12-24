package domain

import scala.util.{Either, Left, Right}
import domain.core.Error

case class StockUnit(value: Int) {
  def isSupplied: Boolean = value > 0
  def decrease: StockUnit = StockUnit(value - 1)
}

object StockUnit {
  def from(stock: Int): Either[Error, StockUnit] = {
    if (stock >= 0) Right(StockUnit(stock))
    else Left(Error("A stock unit cannot be negative"))
  }
}