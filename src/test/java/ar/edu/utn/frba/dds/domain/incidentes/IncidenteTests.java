package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.domain.comunidades.Membresia;
import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona.NotificacionAlMomento;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Etiqueta;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IncidenteTests {
    private Persona persona1;
    private Persona persona2;
    private Comunidad comunidad;
    private Servicio servicio1;
    private Servicio servicio2;
    private Ubicacion ubicacion1;
    private Ubicacion ubicacion2;
    private Provincia buenosAires;
    private Provincia santaFe;
    private Entidad entidad;
    private Establecimiento establecimiento;

    @BeforeEach
    public void init(){
        persona1 = new Persona("Nombre", "Apellido", new Usuario("username", "password"));
        persona2 = new Persona("Nombre2", "Apellido2", new Usuario("username", "password"));

        comunidad = new Comunidad("Comunidad 1");

        servicio1 = new Servicio();
        servicio1.setId(1);
        servicio1.agregarEtiqueta(new Etiqueta("tipo", "baño"));
        servicio1.agregarEtiqueta(new Etiqueta("genero", "hombre"));

        servicio2 = new Servicio();
        servicio2.setId(2);
        servicio2.agregarEtiqueta(new Etiqueta("tipo", "baño"));
        servicio2.agregarEtiqueta(new Etiqueta("genero", "mujer"));

        buenosAires = new Provincia(1, "Buenos Aires");
        ubicacion1 = new Ubicacion(buenosAires);

        santaFe = new Provincia(2, "Santa Fe");
        ubicacion2 = new Ubicacion(santaFe);

        establecimiento = new Establecimiento("esta 1", "establecimiento");
        entidad = new Entidad("entidad 1", "entidad");

        establecimiento.agregarServicio(servicio1);
        establecimiento.agregarServicio(servicio2);
        entidad.agregarEstablecimiento(establecimiento);
    }

    @Test
    @DisplayName("Al generar un incidente, se notifica a los miembros de la comunidad")
    public void abrirIncidenteNotificaMiembros(){
        persona1.setMetodoNotificacion("MAIL");
        persona1.setEmail("mcotens@gmail.com");
        persona1.setTiempoNotificacion(new NotificacionAlMomento());
        persona2.setMetodoNotificacion("WPP");
        persona2.setWhatsapp(1144199146);
        persona2.setTiempoNotificacion(new NotificacionAlMomento());

        comunidad.agregarPersona(persona1);
        comunidad.agregarPersona(persona2);

        ServicioPrestado servicioPrestado = establecimiento.getServiciosPrestados().get(0);

        Incidente incidente = new Incidente(persona1, servicioPrestado);
        incidente.agregarIncidenteComunidad(persona1);

        Assertions.assertEquals(1, comunidad.getIncidentes().size());
    }

    @Test
    @DisplayName("Al cerrar un incidente, se notifica a los miembros de la comunidad")
    public void cerrarIncidenteNotificaMiembros(){
        persona1.setMetodoNotificacion("MAIL");
        persona1.setEmail("mcotens@gmail.com");
        persona1.setTiempoNotificacion(new NotificacionAlMomento());

        persona2.setMetodoNotificacion("WPP");
        persona2.setWhatsapp(1144199146);
        persona2.setTiempoNotificacion(new NotificacionAlMomento());

        Persona persona3 = new Persona("Nombre", "Apellido", new Usuario("username", "password"));
        persona3.setMetodoNotificacion("MAIL");
        persona3.setEmail("mcotens@gmail.com");
        persona3.setTiempoNotificacion(new NotificacionAlMomento());

        //Solo la persona 1 y 2 son miembros, entonces la persona 3 no deberia ser notificada
        comunidad.agregarPersona(persona1);
        comunidad.agregarPersona(persona2);

        ServicioPrestado servicioPrestado = establecimiento.getServiciosPrestados().get(0);

        Incidente incidente = new Incidente(persona1, servicioPrestado);
        incidente.agregarIncidenteComunidad(persona1);

        Assertions.assertEquals(1, comunidad.getIncidentes().size());

        persona2.cerrarIncidente(incidente);

        Assertions.assertEquals(1, comunidad.getIncidentes().size());

        Assertions.assertEquals(1, comunidad.getIncidentes().stream().filter(ipc -> ipc.isEstaCerrado()).toList().size());
        Assertions.assertEquals(0, comunidad.getIncidentes().stream().filter(ipc -> !(ipc.isEstaCerrado())).toList().size());
    }
}
