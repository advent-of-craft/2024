import arrow.core.getOrElse
import com.github.javafaker.Faker
import domain.Toy

class ToyBuilder private constructor(private val stock: Int) {

    companion object {
        fun toysInStock(stock: Int) = ToyBuilder(stock)
    }

    fun build(): Toy =
        Toy.create(Time.TimeProvider, Faker().lorem().word(), stock)
            .getOrElse { throw RuntimeException("Toy creation failed") }
}
