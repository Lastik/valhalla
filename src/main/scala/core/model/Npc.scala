package core.model

import java.util.UUID

import core.model.stats.{Agility, Defence, Health}

case class NpcId(value: String = UUID.randomUUID().toString)

case class Npc(id: NpcId = NpcId(), name: String, health: Health, defence: Defence, agility: Agility) {
  def withHealth(newHealth: Health): Npc = this.copy(health = newHealth)

  def isDead = health <= 0
}

object Npc {
  val Thor = Npc(name = "Thor", defence = 100, health = 200, agility = 50)
  val Odin = Npc(name = "Odin", defence = 80, health = 100, agility = 30)
  val Loki = Npc(name = "Loki", defence = 80, health = 100, agility = 300)

  val Values = List(Thor, Odin, Loki)
}

