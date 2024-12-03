package preparation

import scala.collection.mutable
import scala.collection.mutable.Map

class Gift(
            private val name: String,
            private val weight: Double,
            private val color: String,
            private val material: String
          ) {
  private val attributes: mutable.Map[String, String] = mutable.Map()

  def addAttribute(key: String, value: String): Unit = {
    attributes(key) = value
  }

  def getRecommendedAge: Int = attributes.get("recommendedAge").map(value => value.toInt).getOrElse(0)

  override def toString: String = s"A $color-colored $name weighing $weight kg made in $material"
}
