package org.corgiking.akka.hello;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * 
 * 简单的接收消息处理消息
 *
 */
public class HelloActor extends AbstractActor {

	public static Props props() {
		return Props.create(HelloActor.class);
	}

	/**
	 * 接收的短信
	 *
	 */
	public static class Msg {
		private String msg;

		public Msg(String msg) {
			super();
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		@Override
		public String toString() {
			return "Msg [msg=" + msg + "]";
		}

	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Msg.class, (msg) -> {
			System.out.println(msg.getMsg());
		}).build();
	}

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("helloakka");
		ActorRef helloActor = system.actorOf(HelloActor.props(), "helloActor");

		for (int i = 0; i < 10; ++i) {
			Msg msg = new Msg("actor" + i + " hello akka!");
			helloActor.tell(msg, ActorRef.noSender());
		}

		system.terminate();
		System.out.println("main end !");
	}

}
