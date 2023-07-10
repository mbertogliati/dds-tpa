package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.domain.servicios.Etiqueta;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestInformes {

    private static final OrganismoControl organismoControl = new OrganismoControl("Mi Organismo de Control!");
    private static final EntidadPrestadora entidadPrestadora = new EntidadPrestadora("Mi Entidad Prestadora!");

    private static final Entidad bbva = new Entidad("BBVA","Denominacion 1");
    private static final Entidad mcdonalds = new Entidad("McDonalds","Denominacion 2");
    private static final Entidad burgerKing = new Entidad("BurgerKing", "Denominacion 3");

    private static final Persona persona1 = new Persona("Juan", "Perez");
    private static final Persona persona2 = new Persona("Pedro", "Gomez");

    private static final Ranking ranking1 = new Ranking();
    private static final Ranking ranking2 = new Ranking();

    @BeforeAll
    public static void init() throws IOException {
        organismoControl.setPersonaAInformar(persona1);
        entidadPrestadora.setPersonaAInformar(persona2);
        organismoControl.getEntidadesPrestadoras().add(entidadPrestadora);

        entidadPrestadora.getEntidades().add(bbva);
        entidadPrestadora.getEntidades().add(mcdonalds);

        ranking1.setDescripcion("Ranking 1");
        ranking2.setDescripcion("Ranking 2");

        ranking1.setFechaHoraCreacion(LocalDateTime.now());
        ranking2.setFechaHoraCreacion(LocalDateTime.now());

        ranking1.agregarEntidad(new PuntosPorEntidad(bbva, 50.0));
        ranking1.agregarEntidad(new PuntosPorEntidad(mcdonalds, 20.0));
        ranking1.agregarEntidad(new PuntosPorEntidad(burgerKing, 30.0));

        ranking2.agregarEntidad(new PuntosPorEntidad(bbva, 30.0));
        ranking2.agregarEntidad(new PuntosPorEntidad(mcdonalds, 40.0));
        ranking2.agregarEntidad(new PuntosPorEntidad(burgerKing, 50.0));


        ranking1.ordernar();
        ranking2.ordernar();
    }

    @Test
    @DisplayName("Los informes filtran a los rankings por entidad")
    public void informesFiltranRankingsPorEntidad(){

        Informe informe = new Informe();
        informe.setInformable(entidadPrestadora);
        informe.agregarRankings(ranking1, ranking2);

        List<Ranking> rankings = new ArrayList<>(informe.getRankings());

        System.out.println(informe.getInfo());

        Assertions.assertFalse(rankings.isEmpty());
        rankings.forEach(ranking ->
                Assertions.assertTrue(ranking.contieneEntidad(bbva) && ranking.contieneEntidad(mcdonalds) && !ranking.contieneEntidad(burgerKing)));
    }
}
