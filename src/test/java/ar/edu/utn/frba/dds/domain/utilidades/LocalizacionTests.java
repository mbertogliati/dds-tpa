package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import ar.edu.utn.frba.dds.domain.entidades.Denominacion;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LocalizacionTests {

    @BeforeEach
    public void init(){
    //TODO: ARREGLAR TESTS LOCALIZACION
    }

    @Test
    @DisplayName("Se puede asignar una localizacion a una entidad")
    public void asignarLocalizacionAEntidad(){
        Entidad entidad = new Entidad("entidad prestadora", new Denominacion("entidad"));

            entidad.agregarEstablecimiento(new Establecimiento(new Ubicacion((float)-34.77995323941093, (float)-58.39850705828568), new Denominacion("establecimiento")));
            entidad.agregarEstablecimiento(new Establecimiento(new Ubicacion((float)-34.60364737571995, (float)-58.38158957545822), new Denominacion("establecimiento")));
            entidad.agregarEstablecimiento(new Establecimiento(new Ubicacion((float)-34.568478432086074, (float)-58.47965135917718), new Denominacion("establecimiento")));


        Assertions.assertEquals(7, entidad.getUbicaciones().size());
    }

    @Test
    @DisplayName("Se puede asignar una localizacion a una persona")
    public void asignarLocalizacionAPersona(){
        Persona persona;

            persona = new Persona("Nombre", "Apellido", new Usuario("username", "password"));
            persona.setUbicacion(new Ubicacion((float)-34.77995323941093, (float)-58.39850705828568));

        Assertions.assertEquals("06", persona.getInteres().getUbicacion().getProvincia().getId());
    }

}
