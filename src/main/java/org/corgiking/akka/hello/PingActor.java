package org.corgiking.akka.hello;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.PatternsCS;

public class PingActor extends AbstractActor {

	public static Props props() {
		return Props.create(PingActor.class);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().matchEquals("ping", x -> {
			Thread.sleep(4000);
			getSender().tell("pong", getSelf());
		}).build();
	}
	
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("pingSystem");
		ActorRef pingActor = system.actorOf(PingActor.props());
		
		System.out.println("before ping");
		CompletionStage<Object> cs = PatternsCS.ask(pingActor, "ping", 3000);
		System.out.println("after ping");
		CompletableFuture<Object> jFuture = (CompletableFuture<Object>) cs;
		
		// 每一个方法都返回新的future
		jFuture.exceptionally(e -> {
			System.out.println("exceptionally:  "+e);
			return "default";
		})
		.thenApply(x -> "super " + x)
		.thenAccept(x -> {
			System.out.println(x);
		});
		
		jFuture.handle((ret, throwable) -> {
			if (throwable != null) {
				return "handle default";
			}
			return ret;
		}).thenAccept(ret -> System.out.println(ret));
		
		
		
		System.out.println("end");
	}

}
