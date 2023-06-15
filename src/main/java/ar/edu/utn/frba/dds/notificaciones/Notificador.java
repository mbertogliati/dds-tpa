package ar.edu.utn.frba.dds.notificaciones;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.email.AdapterMAIL;
import ar.edu.utn.frba.dds.notificaciones.email.ApacheCommonsEmail;
import ar.edu.utn.frba.dds.notificaciones.wpp.AdapterWPP;

public class Notificador{
    private static AdapterMAIL enviadorMail = new ApacheCommonsEmail();
    private static AdapterWPP enviadorWhatsapp;

    public static void notificar(Notificable notificable, Persona persona) {
        switch(persona.getMetodoNotificacion()){
            case "WPP": notificarWPP(notificable, persona); break;
            case "MAIL": notificarMAIL(notificable, persona); break;
            default: throw new NoExisteMetodoExcepcion();
        }
    }

    private static void notificarWPP(Notificable notificable, Persona persona){
    }

    private static void notificarMAIL(Notificable notificable, Persona persona){
        enviadorMail.enviarMAIL(notificable.getInfo(), persona.getEmail());
    }
}
