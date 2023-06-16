package ar.edu.utn.frba.dds.notificaciones.wpp;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import ar.edu.utn.frba.dds.notificaciones.EjemploNotificable;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWPP {
    Persona persona;
    Notificable notificable;

    @BeforeEach
    public void init(){
        this.persona = new Persona("Matias", "Cotens", "mcotens@gmail.com", new Usuario("userMatias", "passMatias"));
        this.notificable = new EjemploNotificable();
    }
}
