package org.corgiking.akka.buycandy;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class PlayerActor extends AbstractActor {

	public static Props props() {
		return Props.create(PlayerActor.class);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(BuyCandy.class, (x) -> {
			x.getCounterActor().tell(new CandyCounterActor.TakeCandySignal(), getSelf());
		}).match(BuyResult.class, x -> {
			if (x.buy == true) {
				System.out.println(getSelf() + "购买成功！");
			} else {
//				System.out.println(getSelf() + "购买失败！");
			}
		}).build();
	}

	/**
	 * 买糖果
	 */
	public static class BuyCandy {
		private ActorRef counterActor;

		public BuyCandy(ActorRef counterActor) {
			this.counterActor = counterActor;
		}

		public ActorRef getCounterActor() {
			return counterActor;
		}

		public void setCounterActor(ActorRef counterActor) {
			this.counterActor = counterActor;
		}

	}

	/**
	 * 买糖果结果
	 */
	public static class BuyResult {
		private boolean buy;

		public BuyResult(boolean buy) {
			super();
			this.buy = buy;
		}

		public boolean isBuy() {
			return buy;
		}

		public void setBuy(boolean buy) {
			this.buy = buy;
		}

	}
}
