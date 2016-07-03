package core.service

import akka.actor.Actor
import common.actors.LookupBusinessActor
import core.model._
import core.service.WorldService._
import org.joda.time.DateTime
import common.TimeSpan._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object WorldService {
  val Id = "world-actor"

  case class FindAnyAliveNpc()

  case class GetHeroById(heroId: HeroId)

  case class ScheduleAttack(time: DateTime, attack: AttackNpc)

  case class DecreaseNpcHealth(npcId: NpcId, damage: Damage)
}

class WorldService extends Actor with LookupBusinessActor {

  object InternalMessages {

    case class PerformAttack(attack: AttackNpc)

  }

  var npcById = Npc.Values.map(npc => (npc.id, npc)).toMap

  var heroById = Hero.Values.map(hero => (hero.id, hero)).toMap

  println("Welcome to Valhalla!")

  def receive: Receive = {
    case FindAnyAliveNpc() =>
      sender ! npcById.values.find(!_.isDead)
    case GetHeroById(heroId) =>
      sender ! heroById.getOrElse(heroId, "Can't find Hero with specified id")
    case ScheduleAttack(time, attack) =>
      val now = DateTime.now()
      if (now.isBefore(time)) {
        val timeDelta = (time - now).millis.millis
        context.system.scheduler.scheduleOnce(timeDelta, self, InternalMessages.PerformAttack(attack))
      }
    case InternalMessages.PerformAttack(attack) =>
      attack.call
    case DecreaseNpcHealth(npcId, damage) =>
      npcById.get(npcId) match {
        case Some(npc) =>
          if(!npc.isDead) {
            val updatedNPC = npc.withHealth(npc.health - damage)

            npcById = npcById + (npcId -> updatedNPC)

            if (updatedNPC.isDead) {
              println(s"${npc.name} is dead!")
            }
          }
        case None =>
          throw new IllegalArgumentException("Couldn't find npc with specified id")
      }
  }
}