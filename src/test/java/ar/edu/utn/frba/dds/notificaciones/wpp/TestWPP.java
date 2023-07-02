package ar.edu.utn.frba.dds.notificaciones.wpp;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.EjemploNotificable;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWPP {
    Persona persona;
    Notificable notificable;

    @BeforeEach
    public void init(){
        this.persona = new Persona("Matias", "Cotens");
        this.persona.setEmail("mcotens@gmail.com");
        this.notificable = new EjemploNotificable();
    }

    @Test
    @DisplayName("Se puede enviar un whatsapp usando Twilio")
    public void seEnviaWPPConTwilio(){
        this.persona.setMetodoNotificacion("WPP");
        this.persona.setWhatsapp(1144199146);

        Notificador.notificar(this.notificable, this.persona);
    }
}
