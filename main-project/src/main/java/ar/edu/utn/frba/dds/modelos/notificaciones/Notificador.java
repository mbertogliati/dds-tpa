package ar.edu.utn.frba.dds.modelos.notificaciones;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.email.ApacheCommonsEmail;
import ar.edu.utn.frba.dds.modelos.notificaciones.email.StrategyMAIL;
import ar.edu.utn.frba.dds.modelos.notificaciones.wpp.StrategyWPP;
import ar.edu.utn.frba.dds.modelos.notificaciones.wpp.WhatsappTwilio;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class Notificador {
    @Getter
    private static Map<String, StrategyNotificacion> estrategias;

    static {
        estrategias = new HashMap<String, StrategyNotificacion>();
        StrategyWPP wpp = new StrategyWPP();
        wpp.setAdapter(new WhatsappTwilio());
        estrategias.put("WPP", wpp);

        StrategyMAIL mail = new StrategyMAIL();
        mail.setAdapter(new ApacheCommonsEmail());
        estrategias.put("MAIL", mail);
    }

    public static void agregarEstrategia(String nombre, StrategyNotificacion estrategia){
        estrategias.put(nombre, estrategia);
    }

    public static void notificar(Notificable notificable, Persona persona) {
        notificar(notificable.getInfo(), persona);
    }

    public static void notificar(String mensaje, Persona persona) {
        String metodo = persona.getMetodoNotificacion();
        if(estrategias.containsKey(metodo)){
            estrategias.get(metodo).enviarNotificacion(mensaje, persona);
        }else{
            throw new NoExisteMetodoExcepcion();
        }
    }
}
