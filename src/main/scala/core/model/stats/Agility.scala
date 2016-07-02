package core.model.stats

import scala.util.Random

case class Agility(value: Int){
  def check: Boolean = {

    //The more this coeff is, the more is change to dodge
    val dodgeEfficiencyCoeff = 120.0

    val chanceToDodge = -1.0 / (value / dodgeEfficiencyCoeff + 1.0) + 1.0
    Random.nextFloat() < chanceToDodge
  }
}

object Agility {
  implicit def fromInt(value: Int): Agility = Agility(value)
}
