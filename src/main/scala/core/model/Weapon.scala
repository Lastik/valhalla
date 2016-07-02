package core.model

import scala.concurrent.duration._

sealed trait Weapon {

  def name: String

  def weaponType : WeaponType

  def damage = weaponType.damage

  def attackDuration = weaponType.attackDuration
}

case object Dagger extends Weapon{
  val name = "Dagger"

  val weaponType = LightWeapon
}

case object TwoHandedSword extends Weapon{
  val name = "Two-handed sword"

  val weaponType = HeavyWeapon
}

sealed trait WeaponType{
  def name: String

  def attackDuration: FiniteDuration

  def damage: Damage
}

case object HeavyWeapon extends WeaponType{
  val name = "Heavy"

  val attackDuration = 5 seconds

  val damage: Damage = 100
}

case object LightWeapon extends WeaponType{
  val name = "Light"

  val attackDuration = 2 seconds

  val damage: Damage = 20
}

case class Damage(value: Int){

  def * (coeff: Double) = {
    Damage(value = (value * coeff).toInt)
  }

  override def toString = value.toString
}

object Damage {
  implicit def fromInt(value: Int): Damage = Damage(value)
}
