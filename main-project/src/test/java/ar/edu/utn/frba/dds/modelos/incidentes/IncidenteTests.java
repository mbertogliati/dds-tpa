package ar.edu.utn.frba.dds.modelos.incidentes;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificacionAlMomento;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificador;
import ar.edu.utn.frba.dds.modelos.notificaciones.email.AdapterMAIL;
import ar.edu.utn.frba.dds.modelos.notificaciones.email.StrategyMAIL;
import ar.edu.utn.frba.dds.modelos.notificaciones.wpp.AdapterWPP;
import ar.edu.utn.frba.dds.modelos.notificaciones.wpp.StrategyWPP;
import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
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
    private ServicioPrestado servicioPrestado1;
    private ServicioPrestado servicioPrestado2;
    private Ubicacion ubicacion1;
    private Ubicacion ubicacion2;
    private Provincia buenosAires;
    private Provincia santaFe;
    private Entidad entidad;
    private Establecimiento establecimiento;

    class AdapterWPPMock implements AdapterWPP {
        public void enviarWPP(String mensaje, int telefono) {
            System.out.println("\nSe envió el mensaje: '" + mensaje + "'.\n Al número: '" + String.valueOf(telefono) + "'.");
        }
    }

    class AdapterMAILMock implements AdapterMAIL {
        public void enviarMAIL(String mensaje, String mail) {
            System.out.println("\nSe envió el mensaje: '" + mensaje + "'.\n Al mail: '" + mail + "'.");
        }
    }

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

        var mockWpp = new AdapterWPPMock();
        var strategy = new StrategyWPP();
        strategy.setAdapter(mockWpp);
        Notificador.agregarEstrategia("WPP",strategy);

        var mockMail = new AdapterMAILMock();
        var strategyMail = new StrategyMAIL();
        strategyMail.setAdapter(mockMail);
        Notificador.agregarEstrategia("MAIL",strategyMail);
    }

    @Test
    @DisplayName("Al generar un incidente, se notifica a los miembros de la comunidad")
    public void abrirIncidenteNotificaMiembros(){
        persona1.setMetodoNotificacion("MAIL");
        persona1.setEmail("mcotens@gmail.com");
        persona1.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());
        persona2.setMetodoNotificacion("WPP");
        persona2.setWhatsapp(1144199146);
        persona2.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

        comunidad.agregarPersona(persona1);
        comunidad.agregarPersona(persona2);

        ServicioPrestado servicioPrestado = establecimiento.getServiciosPrestados().get(0);

        Incidente incidente = new Incidente(persona1);
        incidente.agregarServiciosPrestados(servicioPrestado);

        incidente.agregarIncidenteComunidad();

        Assertions.assertEquals(1, comunidad.getIncidentes().size());
    }

    @Test
    @DisplayName("Al cerrar un incidente, se notifica a los miembros de la comunidad")
    public void cerrarIncidenteNotificaMiembros(){
        persona1.setMetodoNotificacion("MAIL");
        persona1.setEmail("mcotens@gmail.com");
        persona1.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

        persona2.setMetodoNotificacion("WPP");
        persona2.setWhatsapp(1144199146);
        persona2.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

        Persona persona3 = new Persona("Nombre", "Apellido");
        persona3.setMetodoNotificacion("MAIL");
        persona3.setEmail("mcotens@gmail.com");
        persona3.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

        //Solo la persona 1 y 2 son miembros, entonces la persona 3 no deberia ser notificada
        comunidad.agregarPersona(persona1);
        comunidad.agregarPersona(persona2);

        ServicioPrestado servicioPrestado = establecimiento.getServiciosPrestados().get(0);

        Incidente incidente = new Incidente(persona1);
        incidente.agregarServiciosPrestados(servicioPrestado);
        incidente.agregarIncidenteComunidad();

        Assertions.assertEquals(1, comunidad.getIncidentes().size());

        persona2.cerrarIncidente(incidente);

        Assertions.assertEquals(1, comunidad.getIncidentes().size());
    }

    @Test
    @DisplayName("Se pueden consultar incidentes por estado")
    public void sePuedeConsultarIncidentesPorEstado(){
        persona1.setMetodoNotificacion("MAIL");
        persona1.setEmail("mcotens@gmail.com");
        persona1.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

        persona2.setMetodoNotificacion("WPP");
        persona2.setWhatsapp(1144199146);
        persona2.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

        Persona persona3 = new Persona("Nombre", "Apellido");
        persona3.setMetodoNotificacion("MAIL");
        persona3.setEmail("mcotens@gmail.com");
        persona3.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

        //Solo la persona 1 y 2 son miembros, entonces la persona 3 no deberia ser notificada
        comunidad.agregarPersona(persona1);
        comunidad.agregarPersona(persona2);

        ServicioPrestado servicioPrestado = establecimiento.getServiciosPrestados().get(0);

        Incidente incidente = new Incidente(persona1);
        incidente.agregarServiciosPrestados(servicioPrestado);
        incidente.agregarIncidenteComunidad();

        Assertions.assertEquals(1, comunidad.getIncidentes().size());

        persona2.cerrarIncidente(incidente);

        Assertions.assertEquals(1, comunidad.getIncidentes().size());

        Assertions.assertEquals(1, comunidad.getIncidentes().stream().filter(ipc -> ipc.isEstaCerrado()).toList().size());
        Assertions.assertEquals(0, comunidad.getIncidentes().stream().filter(ipc -> !(ipc.isEstaCerrado())).toList().size());
    }



}
