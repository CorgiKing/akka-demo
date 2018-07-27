package org.corgiking.akka.buycandy;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class RushBuy {

	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("rushBuy");

		ActorRef candyCounter = actorSystem.actorOf(CandyCounterActor.props(100), "candyCounter");

		for (int i = 0; i < 1000; ++i) {
			ActorRef player = actorSystem.actorOf(PlayerActor.props(), "player" + i);
			player.tell(new PlayerActor.BuyCandy(candyCounter), ActorRef.noSender());
			
		}
		
		
		System.out.println("main end!");
	}

}
