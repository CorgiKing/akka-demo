package org.corgiking.akka.buycandy;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class CandyCounterActor extends AbstractActor {
	private int total = 100;

	public CandyCounterActor(int total) {
		this.total = total;
	}

	public static Props props(int total) {
		return Props.create(CandyCounterActor.class, () -> new CandyCounterActor(total));
	}

	public static class TakeCandySignal {
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(TakeCandySignal.class, (x) -> {
			if (total > 0) {
				--total;
				getSender().tell(new PlayerActor.BuyResult(true), getSelf());
			} else {
				getSender().tell(new PlayerActor.BuyResult(false), getSelf());
			}
		}).build();
	}

}
