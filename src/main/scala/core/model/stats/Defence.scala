package core.model.stats

import core.model.Damage

case class Defence(value: Int) {

  def calculateWentThroughDamage(damage: Damage): Damage = {
    damage * (-1.0 / (value / 5.0 + 1.0) + 1.0)
  }
}

object Defence {
  implicit def fromInt(value: Int): Defence = Defence(value)
}
