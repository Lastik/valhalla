package core.model.stats

import core.model.Damage

case class Defence(value: Int) {

  def calculateWentThroughDamage(damage: Damage): Damage = {

    //The more this coeff is, the more effective is defence
    val defenceEfficiencyCoeff = 50.0

    damage * (-1.0 / (value / defenceEfficiencyCoeff + 1.0) + 1.0)
  }
}

object Defence {
  implicit def fromInt(value: Int): Defence = Defence(value)
}
