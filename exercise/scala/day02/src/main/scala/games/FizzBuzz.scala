package games

object FizzBuzz {
  val MIN: Int = 1
  val MAX: Int = 100

  private val FIZZBUZZ: Int = 15
  private val FIZZ: Int = 3
  private val BUZZ: Int = 5

  def convert(input: Int): Option[String] = {
    if (isOutOfRange(input)) None
    else Some(convertSafely(input))
  }

  private def convertSafely(input: Int): String = input match {
    case _ if `is`(FIZZBUZZ, input) => "FizzBuzz"
    case _ if `is`(FIZZ, input) => "Fizz"
    case _ if `is`(BUZZ, input) => "Buzz"
    case _ => input.toString
  }

  private def `is`(divisor: Int, input: Int): Boolean = input % divisor == 0

  private def isOutOfRange(input: Int): Boolean = input < MIN || input > MAX
}