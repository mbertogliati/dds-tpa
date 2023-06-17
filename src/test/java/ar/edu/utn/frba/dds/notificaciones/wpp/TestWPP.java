package ar.edu.utn.frba.dds.notificaciones.wpp;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import ar.edu.utn.frba.dds.notificaciones.EjemploNotificable;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import ar.edu.utn.frba.dds.notificaciones.email.AdapterMAIL;
import ar.edu.utn.frba.dds.notificaciones.email.ApacheCommonsEmail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class TestWPP {
    Persona persona;
    Notificable notificable;

    @BeforeEach
    public void init(){
        this.persona = new Persona("Matias", "Cotens", "mcotens@gmail.com", new Usuario("userMatias", "passMatias"));
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
