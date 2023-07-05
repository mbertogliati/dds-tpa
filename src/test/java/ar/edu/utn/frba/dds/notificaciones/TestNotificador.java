package ar.edu.utn.frba.dds.notificaciones;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.email.AdapterMAIL;
import ar.edu.utn.frba.dds.notificaciones.email.StrategyMAIL;
import ar.edu.utn.frba.dds.notificaciones.wpp.AdapterWPP;
import ar.edu.utn.frba.dds.notificaciones.wpp.StrategyWPP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestNotificador {
    Persona persona;
    Notificable notificable;

    @BeforeEach
    public void init() {
        this.persona = new Persona("Matias", "Cotens");
        this.persona.setEmail("mcotens@gmail.com");
        this.notificable = new EjemploNotificable();
    }

    @Test
    @DisplayName("No se puede enviar una notificacion a un método que no sea WPP ni MAIL")
    public void noSeEnviaMetodoIncorrecto(){
        this.persona.setMetodoNotificacion("E-MAIL");

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            Notificador.notificar(this.notificable, this.persona);
        });

        Assertions.assertEquals("No existe el método de notificación indicado", thrown.getMessage());
    }
}
