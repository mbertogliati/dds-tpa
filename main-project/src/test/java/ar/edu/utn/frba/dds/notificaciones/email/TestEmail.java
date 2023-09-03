package ar.edu.utn.frba.dds.notificaciones.email;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.EjemploNotificable;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import ar.edu.utn.frba.dds.notificaciones.wpp.AdapterWPP;
import ar.edu.utn.frba.dds.notificaciones.wpp.StrategyWPP;
import ar.edu.utn.frba.dds.notificaciones.wpp.TestWPP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestEmail {
    Persona persona;
    Notificable notificable;

    class AdapterMAILMock implements AdapterMAIL{
        public void enviarMAIL(String mensaje, String mail) {
            System.out.println("\nSe envió el mensaje: '" + mensaje + "'.\n Al mail: '" + mail + "'.");
        }
    }

    @BeforeEach
    public void init(){
        this.persona = new Persona(
                "Matias",
                "Cotens"
        );
        this.persona.setEmail("mcotens@gmail.com");
        this.notificable = new EjemploNotificable();

        var mockMail = new AdapterMAILMock();
        var strategy = new StrategyMAIL();
        strategy.setAdapter(mockMail);
        Notificador.agregarEstrategia("MAIL",strategy);
    }

    @Test
    @DisplayName("Se puede enviar un mail usando Notificador")
    public void seEnviaMailConNotificador(){
        this.persona.setMetodoNotificacion("MAIL");
        Notificador.notificar(this.notificable, this.persona);
    }
}
