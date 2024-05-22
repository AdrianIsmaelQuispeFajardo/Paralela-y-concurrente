package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public final class Main {

    public static ActorRef actorSupervisor;
    public static ActorRef espadachin1;
    public static ActorRef espadachin2;
    public static ActorRef herrero;
    public static ActorRef minero;
    public static ActorSystem actorSystem;

    private static final long TIEMPO_ESPERA = 1000;

    private Main() {}

    public static void main(String[] args) throws InterruptedException {
        actorSystem = ActorSystem.create("ActorSystem");
        espadachin1=actorSystem.actorOf(Props.create(Espadachin.class), "espadachin1");
        espadachin2=actorSystem.actorOf(Props.create(Espadachin.class), "espadachin2");
        herrero = actorSystem.actorOf(Props.create(Herrero.class), "herrero");
        minero = actorSystem.actorOf(Props.create(Minero.class), "minero");
        actorSupervisor = actorSystem.actorOf(Props.create(Supervisor.class), "actorSupervisor");
        espadachin1.tell(Espadachin.Mensaje.ESPADA_ROTA, ActorRef.noSender());
        Thread.sleep(TIEMPO_ESPERA);
        espadachin2.tell(Espadachin.Mensaje.ESPADA_ROTA, ActorRef.noSender());
    }
}
