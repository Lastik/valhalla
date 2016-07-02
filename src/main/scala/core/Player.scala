package core

import akka.actor.Actor
import akka.pattern.ask
import common.TimeInterval
import common.TimeSpan._
import common.actors.LookupBusinessActor
import core.model.{AttackNpc, Hero, HeroId}
import core.service.WorldService
import core.service.WorldService.ScheduleAttack
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

class Player(name: String, heroId: HeroId) extends Actor with LookupBusinessActor with DefaultTimeout {

  object InternalMessages{
    case class ScheduleAttacks()
  }

  val worldService = lookupByContext(WorldService.Id)

  val scheduleAttacksFrequency = 20 seconds

  val scheduleAttacksScheduler = context.system.scheduler.schedule(
    0 seconds, scheduleAttacksFrequency, self, InternalMessages.ScheduleAttacks())

  var prevAttackTimeOpt: Option[DateTime] = None

  getHeroById(heroId).map{
    hero => {
      println(s"Player $name logged in as ${hero.name}.")
    }
  }

  override def receive: Receive = {
    case InternalMessages.ScheduleAttacks() =>

      val now = DateTime.now()

      val attacksScheduleTimeInterval = TimeInterval(
        start = now,
        end = now + scheduleAttacksFrequency
      )

      getHeroById(heroId).map{
        hero => {
          val attackFrequency = hero.weapon.attackDuration

          val firstAttackTime = prevAttackTimeOpt.getOrElse(attacksScheduleTimeInterval.start) + attackFrequency

          val attacksCount = ((attacksScheduleTimeInterval.end - firstAttackTime) / attackFrequency).toInt

          val attackWithTimePairs = (0 until attacksCount).map(attackIndex => {
            val attackTime = firstAttackTime + (attackFrequency * attackIndex)
            AttackWithTimePair(attackTime, AttackNpc(heroId)(context.system))
          })

          attackWithTimePairs.foreach(attackWithTimePair =>{
            worldService ! ScheduleAttack(attackWithTimePair.time, attackWithTimePair.attackNpc)
          })

          prevAttackTimeOpt = attackWithTimePairs.lastOption.map(_.time)
        }
      }
  }

  override def postStop() = {
    scheduleAttacksScheduler.cancel()
  }

  def getHeroById(heroId: HeroId): Future[Hero] = {
    worldService.ask(WorldService.GetHeroById(heroId)).mapTo[Hero]
  }
}

case class AttackWithTimePair(time:DateTime, attackNpc: AttackNpc)

