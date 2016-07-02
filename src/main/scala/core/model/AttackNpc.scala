package core.model

import java.util.concurrent.Callable

import akka.actor.ActorSystem
import akka.pattern.ask
import common.actors.LookupBusinessActor
import core.DefaultTimeout
import core.service.WorldService
import core.service.WorldService.{FindAnyAliveNpc, GetHeroById}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case class AttackNpc(heroId: HeroId)(implicit actorSystem: ActorSystem)
  extends Callable[Future[Unit]] with LookupBusinessActor with DefaultTimeout  {

  val worldService = lookup(WorldService.Id)

  def call = {
    for {
      hero <- getHeroById(heroId)
      npcOpt <- findAnyAliveNpc()
    } yield {
      npcOpt match{
        case Some(npc) =>
          val isAgilityCheckPassed = npc.agility.check

          if (isAgilityCheckPassed) {
            println(s"${hero.name}'s attack to ${npc.name} ended up with dodge!")
          }
          else {
            val wentThroughDamage = npc.defence.calculateWentThroughDamage(hero.weapon.damage)

            worldService ! WorldService.DecreaseNpcHealth(npcId = npc.id, wentThroughDamage)

            println(s"${hero.name}'s attack to ${npc.name} caused $wentThroughDamage damage!")
          }

        case None =>
          println(s"There is no one left to attack :(")
      }
    }
  }

  def getHeroById(heroId: HeroId): Future[Hero] = {
    worldService.ask(GetHeroById(heroId)).mapTo[Hero]
  }

  def findAnyAliveNpc(): Future[Option[Npc]] = {
    worldService.ask(FindAnyAliveNpc()).mapTo[Option[Npc]]
  }
}