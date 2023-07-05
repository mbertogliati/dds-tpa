package ar.edu.utn.frba.dds.notificaciones;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.email.StrategyMAIL;
import ar.edu.utn.frba.dds.notificaciones.wpp.StrategyWPP;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Notificador {
    @Getter
    private static Map<String, StrategyNotificacion> estrategias;

    static {
        estrategias = new HashMap<String, StrategyNotificacion>();
        //estrategias.put("WPP", new StrategyWPP());
        //estrategias.put("MAIL", new StrategyMAIL());
    }

    public static void notificar(Notificable notificable, Persona persona) {
        String metodo = persona.getMetodoNotificacion();
        if(estrategias.containsKey(metodo)){
            estrategias.get(metodo).enviarNotificacion(notificable.getInfo(), persona);
        }else{
            throw new NoExisteMetodoExcepcion();
        }
    }
}
