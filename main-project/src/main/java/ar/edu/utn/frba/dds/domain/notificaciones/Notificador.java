package ar.edu.utn.frba.dds.domain.notificaciones;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class Notificador {
    @Getter
    private static Map<String, StrategyNotificacion> estrategias;

    static {
        estrategias = new HashMap<String, StrategyNotificacion>();
    }

    public static void agregarEstrategia(String nombre, StrategyNotificacion estrategia){
        estrategias.put(nombre, estrategia);
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
