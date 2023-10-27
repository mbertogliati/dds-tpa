package ar.edu.utn.frba.dds.notificaciones;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.EjemploNotificable;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        System.out.println("No existe el método de notificación indicado");
    }
}
