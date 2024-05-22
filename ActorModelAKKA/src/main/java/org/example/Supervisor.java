package org.example;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import static org.example.Main.espadachin1;
import static org.example.Main.espadachin2;


public class Supervisor extends UntypedAbstractActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private int n = 2;

    public enum Mensaje {
        ESPADA_LISTA
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        espadachin1.tell(Espadachin.Mensaje.ESPADA_ROTA, ActorRef.noSender());
        //Thread.sleep(Main.TIEMPO_ESPERA);
        espadachin2.tell(Espadachin.Mensaje.ESPADA_ROTA, ActorRef.noSender());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("[Espadachin] ha recibido el mensaje: \"{}\".", message);

        if (message == Mensaje.ESPADA_LISTA) {
            n--;
            if (n == 0) {
                getContext().getSystem().terminate();
            }
        } else {
            unhandled(message);
        }
    }
}
