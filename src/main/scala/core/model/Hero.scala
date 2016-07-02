package core.model

import java.util.UUID

case class HeroId(value: String = UUID.randomUUID().toString){
  override def toString = value.toString
}

case class Hero(id: HeroId = HeroId(), name: String, weapon: Weapon)

object Hero {
  val Biern = Hero(name = "Biern", weapon = TwoHandedSword)
  val Ragnar = Hero(name = "Ragnar", weapon = Dagger)

  val Values = List(Biern, Ragnar)
}
