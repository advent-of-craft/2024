package usecases

import com.github.javafaker.Faker
import domain.Toy

class ToyBuilder private(private val stock: Int) {
  def build(): Toy =
    Toy.create(Time.timeProvider, Faker().lorem().word(), stock)
      .getOrElse(throw new RuntimeException("Toy creation failed"))
}

object ToyBuilder {
  def toysInStock(stock: Int): ToyBuilder = new ToyBuilder(stock)
}