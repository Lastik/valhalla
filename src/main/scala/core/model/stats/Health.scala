package core.model.stats

import core.model.Damage

case class Health(value: Int){
  def - (damage: Damage) = copy(value = value - damage.value)

  def <= (someVal: Double) = value <= someVal
}

object Health {
  implicit def fromInt(value: Int): Health = Health(value)
}
