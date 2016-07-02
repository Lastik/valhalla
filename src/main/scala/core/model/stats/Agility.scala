package core.model.stats

import scala.util.Random

case class Agility(value: Int){
  def check: Boolean = {
    val chanceToDodge = -1.0 / (value / 100.0 + 1.0) + 1.0
    Random.nextFloat() < chanceToDodge
  }
}

object Agility {
  implicit def fromInt(value: Int): Agility = Agility(value)
}
