package ar.edu.utn.frba.dds.notificaciones.email;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.EjemploNotificable;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestEmail {
    Persona persona;
    Notificable notificable;

    @BeforeEach
    public void init(){
        this.persona = new Persona(
                "Matias",
                "Cotens"
        );
        this.persona.setEmail("mcotens@gmail.com");
        this.notificable = new EjemploNotificable();
    }

    @Test
    @DisplayName("Se puede enviar un mail usando ApacheCommonsEmail")
    public void seEnviaMailConApacheCommonsEmail() {
        AdapterMAIL enviador = new ApacheCommonsEmail();
        enviador.enviarMAIL("mensaje de prueba", "mcotens@gmail.com");
    }

    @Test
    @DisplayName("Se puede enviar un mail usando Notificador")
    public void seEnviaMailConNotificador(){
        this.persona.setMetodoNotificacion("MAIL");
        Notificador.notificar(this.notificable, this.persona);
    }
}
