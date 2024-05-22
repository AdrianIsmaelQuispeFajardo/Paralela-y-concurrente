package org.example;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Espadachin extends UntypedAbstractActor {
    public enum Mensaje {
        ESPADA_NUEVA,
        ESPADA_ROTA
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object o) {
        log.info("[Espadachin] ha recibido el mensaje: \"{}\".", o);

        if (o == Mensaje.ESPADA_ROTA) {
            Main.herrero.tell(Herrero.Mensaje.CREAR_ESPADA, getSelf());
        } else if (o == Mensaje.ESPADA_NUEVA) {
            getContext().stop(getSelf());
            Main.actorSupervisor.tell(Supervisor.Mensaje.ESPADA_LISTA, ActorRef.noSender());
        } else {
            unhandled(o);
        }
    }
}
