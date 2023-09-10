package ar.edu.utn.frba.dds.notificaciones.wpp;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.notificaciones.EjemploNotificable;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificable;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificador;
import ar.edu.utn.frba.dds.domain.notificaciones.wpp.AdapterWPP;
import ar.edu.utn.frba.dds.domain.notificaciones.wpp.StrategyWPP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWPP {
    Persona persona;
    Notificable notificable;

    class AdapterWPPMock implements AdapterWPP {
        public void enviarWPP(String mensaje, int telefono) {
            System.out.println("\nSe envió el mensaje: '" + mensaje + "'.\n Al número: '" + String.valueOf(telefono) + "'.");
        }
    }

    @BeforeEach
    public void init(){
        this.persona = new Persona("Matias", "Cotens");
        this.persona.setEmail("mcotens@gmail.com");
        this.notificable = new EjemploNotificable();
        var mockWpp = new AdapterWPPMock();
        var strategy = new StrategyWPP();
        strategy.setAdapter(mockWpp);
        Notificador.agregarEstrategia("WPP",strategy);
    }

    @Test
    @DisplayName("Se puede enviar un whatsapp usando Twilio")
    public void seEnviaWPPConTwilio(){
        this.persona.setMetodoNotificacion("WPP");
        this.persona.setWhatsapp(1144199146);

        Notificador.notificar(this.notificable, this.persona);
    }
}
