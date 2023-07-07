package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.domain.servicios.Etiqueta;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestGeneradorRankings {
    private static GeneradorRanking generadorRanking;
    private static final Entidad bbva = new Entidad("BBVA","Denominacion 1");
    private static final Entidad mcdonalds = new Entidad("McDonalds","Denominacion 2");
    private static final Entidad burgerKing = new Entidad("BurgerKing", "Denominacion 3");

    private static final Establecimiento sucursal1BBVA = new Establecimiento("Sucursal 1", "Sucursal 1");
    private static final Establecimiento sucursal2MC = new Establecimiento("Sucursal 2", "Sucursal 2");
    private static final Establecimiento estacion1BK = new Establecimiento("Estacion 1", "Estacion 1");
    private static final Establecimiento estacion2MC = new Establecimiento("Estacion 2", "Estacion 2");
    private static final Establecimiento estacion3BK = new Establecimiento("Estacion 3", "Estacion 3");

    private static final Servicio banioHombreBebe = new Servicio(new Etiqueta("tipoServicio","banio"), new Etiqueta("genero","hombre"), new Etiqueta("edad","bebe"));
    private static final Servicio escaleraMolinete = new Servicio(new Etiqueta("tipoServicio","escalera"), new Etiqueta("tipoAcceso","molinete"));
    private static final Servicio escaleraMolineteAncho = new Servicio(new Etiqueta("tipoServicio","escalera"), new Etiqueta("tipoAcceso","molinete"), new Etiqueta("ancho","ancho"));
    private static final Servicio banioSinGeneroAscensor = new Servicio(new Etiqueta("tipoServicio","banio"), new Etiqueta("tipoAcceso","ascensor"));

    private static final ServicioPrestado servicioPrestado1BBVA = new ServicioPrestado(banioHombreBebe);
    private static final ServicioPrestado servicioPrestado2MC = new ServicioPrestado(escaleraMolinete);
    private static final ServicioPrestado servicioPrestado3BK = new ServicioPrestado(escaleraMolineteAncho);
    private static final ServicioPrestado servicioPrestado4BK = new ServicioPrestado(banioSinGeneroAscensor);

    private static final Incidente incidente1BBVA = new Incidente();
    private static final Incidente incidente2MC = new Incidente();
    private static final Incidente incidente3BK = new Incidente();
    private static final Incidente incidente4BK = new Incidente();

    private static final Incidente incidente4BK_2 = new Incidente();
    private static final Incidente incidente1BBVA_2 = new Incidente();
    private static final Incidente incidente6MC = new Incidente();

    private static final IncidentePorComunidad incidentePC1_BBVA = new IncidentePorComunidad(incidente1BBVA);
    private static final IncidentePorComunidad incidentePC2_MC = new IncidentePorComunidad(incidente2MC);
    private static final IncidentePorComunidad incidentePC3_BK = new IncidentePorComunidad(incidente3BK);
    private static final IncidentePorComunidad incidentePC4_BK = new IncidentePorComunidad(incidente4BK);
    private static final IncidentePorComunidad incidentePC4_BK_2 = new IncidentePorComunidad(incidente4BK_2);
    private static final IncidentePorComunidad incidentePC1_BBVA_2 = new IncidentePorComunidad(incidente1BBVA_2);
    private static final IncidentePorComunidad incidentePC6_MC = new IncidentePorComunidad(incidente6MC);




    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeAll
    public static void init() throws IOException {
        sucursal1BBVA.setEntidad(bbva);
        sucursal2MC.setEntidad(mcdonalds);
        estacion1BK.setEntidad(burgerKing);
        estacion2MC.setEntidad(mcdonalds);
        estacion3BK.setEntidad(burgerKing);

        servicioPrestado1BBVA.setEstablecimiento(sucursal1BBVA);
        servicioPrestado2MC.setEstablecimiento(sucursal2MC);
        servicioPrestado3BK.setEstablecimiento(estacion1BK);
        servicioPrestado4BK.setEstablecimiento(estacion3BK);


        incidente1BBVA.getServiciosAfectados().add(servicioPrestado1BBVA);
        incidente1BBVA_2.getServiciosAfectados().add(servicioPrestado1BBVA);

        incidente2MC.getServiciosAfectados().add(servicioPrestado2MC);
        incidente6MC.getServiciosAfectados().add(servicioPrestado2MC);

        incidente3BK.getServiciosAfectados().add(servicioPrestado3BK);
        incidente4BK.getServiciosAfectados().add(servicioPrestado4BK);
        incidente4BK_2.getServiciosAfectados().add(servicioPrestado4BK);





        incidente1BBVA.setFechaHoraApertura(LocalDateTime.parse("2020-01-01 00:00",formatter));
        incidente1BBVA_2.setFechaHoraApertura(LocalDateTime.parse("2020-01-01 12:00",formatter));

        incidente2MC.setFechaHoraApertura(LocalDateTime.parse("2020-01-02 00:00",formatter));
        incidente6MC.setFechaHoraApertura(LocalDateTime.parse("2020-01-06 00:00",formatter));

        incidente3BK.setFechaHoraApertura(LocalDateTime.parse("2020-01-03 00:00",formatter));
        incidente4BK.setFechaHoraApertura(LocalDateTime.parse("2020-01-04 00:00",formatter));
        incidente4BK_2.setFechaHoraApertura(LocalDateTime.parse("2020-01-05 12:00",formatter));



    }

    @Test
    @DisplayName("Test PromedioEntreAperturaYCierre")
    public void promedioAperturaCierre(){
        generadorRanking = new GeneradorRanking("Ranking de Entidades con Mayor Promedio entre apertura y cierre");
        generadorRanking.setGeneradorPuntos(new PromedioEntreAperturaYCierre());

        incidentePC1_BBVA.setFechaHoraCierre(LocalDateTime.parse("2020-01-01 04:00",formatter));
        incidentePC1_BBVA_2.setFechaHoraCierre(LocalDateTime.parse("2020-01-01 15:00",formatter));

        incidentePC2_MC.setFechaHoraCierre(LocalDateTime.parse("2020-01-02 01:00",formatter));
        incidentePC6_MC.setFechaHoraCierre(LocalDateTime.parse("2020-01-06 01:00",formatter));

        incidentePC3_BK.setFechaHoraCierre(LocalDateTime.parse("2020-01-03 02:00",formatter));
        incidentePC4_BK.setFechaHoraCierre(LocalDateTime.parse("2020-01-04 01:00",formatter));



        Ranking ranking = generadorRanking.generarRanking(new ArrayList<>(List.of(
                incidentePC1_BBVA,
                incidentePC2_MC,
                incidentePC3_BK,
                incidentePC4_BK,
                incidentePC1_BBVA_2,
                incidentePC6_MC
                )));

        System.out.print(ranking);
        Assertions.assertEquals(3, ranking.getPuntosPorEntidad().size());

        Assertions.assertEquals(bbva, ranking.getPuntosPorEntidad().get(0).getEntidad());
        Assertions.assertEquals(210, ranking.puntosDe(bbva));

        Assertions.assertEquals(burgerKing, ranking.getPuntosPorEntidad().get(1).getEntidad());
        Assertions.assertEquals(90, ranking.puntosDe(burgerKing));

        Assertions.assertEquals(mcdonalds, ranking.getPuntosPorEntidad().get(2).getEntidad());
        Assertions.assertEquals(60, ranking.puntosDe(mcdonalds));

    }

    @Test
    @DisplayName("Test MasIncidentesEnSemana")
    public void masIncidentesEnSemana(){
        generadorRanking = new GeneradorRanking("Ranking de Entidades con más incidentes en semana");
        generadorRanking.setGeneradorPuntos(new MasIncidentesEnSemana());

        //incidente4BK.setFechaHoraApertura(LocalDateTime.parse("2020-01-03 00:00",formatter));

        Ranking ranking = generadorRanking.generarRanking(new ArrayList<>(List.of(
                incidentePC1_BBVA,
                incidentePC2_MC,
                incidentePC3_BK,
                incidentePC4_BK,
                incidentePC4_BK_2,
                incidentePC1_BBVA_2,
                incidentePC6_MC
        )));

        System.out.print(ranking);
        Assertions.assertEquals(3, ranking.getPuntosPorEntidad().size());

        Assertions.assertEquals(burgerKing, ranking.getPuntosPorEntidad().get(0).getEntidad());
        Assertions.assertEquals(3, ranking.puntosDe(burgerKing));

        Assertions.assertEquals(mcdonalds, ranking.getPuntosPorEntidad().get(1).getEntidad());
        Assertions.assertEquals(2, ranking.puntosDe(mcdonalds));

        Assertions.assertEquals(bbva, ranking.getPuntosPorEntidad().get(2).getEntidad());
        Assertions.assertEquals(1, ranking.puntosDe(bbva));
    }
}
