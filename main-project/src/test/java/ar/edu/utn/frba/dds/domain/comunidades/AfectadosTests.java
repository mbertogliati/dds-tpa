package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.incidentes.IncidenteTests;
import ar.edu.utn.frba.dds.domain.servicios.Etiqueta;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import ar.edu.utn.frba.dds.notificaciones.email.StrategyMAIL;
import ar.edu.utn.frba.dds.notificaciones.wpp.StrategyWPP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AfectadosTests {
    private Persona persona1;
    private Persona persona2;
    private Comunidad comunidad;
    private Servicio servicio1;
    private Servicio servicio2;
    private ServicioPrestado servicioPrestado1;
    private ServicioPrestado servicioPrestado2;
    private Ubicacion ubicacion1;
    private Ubicacion ubicacion2;
    private Provincia buenosAires;
    private Provincia santaFe;
    private Entidad entidad;
    private Establecimiento establecimiento;

    @BeforeEach
    public void init(){
        persona1 = new Persona("Nombre", "Apellido");
        persona2 = new Persona("Nombre2", "Apellido2");

        comunidad = new Comunidad("Comunidad 1");

        servicio1 = new Servicio();
        servicio1.setId(1);
        servicio1.agregarEtiqueta(new Etiqueta("tipo", "baño"));
        servicio1.agregarEtiqueta(new Etiqueta("genero", "hombre"));

        servicio2 = new Servicio();
        servicio2.setId(2);
        servicio2.agregarEtiqueta(new Etiqueta("tipo", "baño"));
        servicio2.agregarEtiqueta(new Etiqueta("genero", "mujer"));

        buenosAires = new Provincia("1", "Buenos Aires");
        ubicacion1 = new Ubicacion(buenosAires);

        santaFe = new Provincia("2", "Santa Fe");
        ubicacion2 = new Ubicacion(santaFe);

        establecimiento = new Establecimiento("esta 1", "establecimiento");
        entidad = new Entidad("entidad 1", "entidad");

        establecimiento.agregarServicio(servicio1);
        establecimiento.agregarServicio(servicio2);
        entidad.agregarEstablecimiento(establecimiento);
    }

    @Test
    @DisplayName("Se puede ser afectado para un servicio en una comunidad")
    public void unMiembroPuedeSerAfectadoParaUnServicioEnUnaComunidad(){
        comunidad.agregarServicio(servicio1);

        comunidad.agregarPersona(persona1);
        persona1.getMembresias().get(0).agregarServicioAfectado(servicio1);

        Assertions.assertEquals(1, comunidad.getMembresias().stream().filter(m -> m.estaAfectado(servicio1)).toList().size());
        Assertions.assertEquals(0, comunidad.getMembresias().stream().filter(m -> !(m.estaAfectado(servicio1))).toList().size());
    }

    @Test
    @DisplayName("Se puede ser observador para un servicio en una comunidad")
    public void unMiembroPuedeSerObservadorParaUnServicioEnUnaComunidad(){
        comunidad.agregarServicio(servicio1);

        comunidad.agregarPersona(persona1);
        persona1.getMembresias().get(0).agregarServicioObservado(servicio1);

        Assertions.assertEquals(0, comunidad.getMembresias().stream().filter(m -> m.estaAfectado(servicio1)).toList().size());
        Assertions.assertEquals(1, comunidad.getMembresias().stream().filter(m -> !(m.estaAfectado(servicio1))).toList().size());
    }

    @Test
    @DisplayName("Se puede ser observador para un servicio y afectado para otro en una comunidad")
    public void unMiembroPuedeSerObservadorParaUnServicioYAfectadoParaOtroEnUnaComunidad(){
        comunidad.agregarServicio(servicio1);
        comunidad.agregarServicio(servicio2);

        comunidad.agregarPersona(persona1);
        persona1.getMembresias().get(0).agregarServicioObservado(servicio1);
        persona1.getMembresias().get(0).agregarServicioAfectado(servicio2);

        Assertions.assertEquals(0, comunidad.getMembresias().stream().filter(m -> m.estaAfectado(servicio1)).toList().size());
        Assertions.assertEquals(1, comunidad.getMembresias().stream().filter(m -> !(m.estaAfectado(servicio1))).toList().size());

        Assertions.assertEquals(1, comunidad.getMembresias().stream().filter(m -> m.estaAfectado(servicio2)).toList().size());
        Assertions.assertEquals(0, comunidad.getMembresias().stream().filter(m -> !(m.estaAfectado(servicio2))).toList().size());
    }
}
