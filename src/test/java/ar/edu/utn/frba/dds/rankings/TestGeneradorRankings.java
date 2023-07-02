package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.domain.rankings.GeneradorRanking;
import ar.edu.utn.frba.dds.domain.rankings.MasIncidentesEnSemana;
import ar.edu.utn.frba.dds.domain.rankings.PromedioEntreAperturaYCierre;
import ar.edu.utn.frba.dds.domain.rankings.Ranking;
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
    private static Entidad bbva = new Entidad("BBVA","Denominacion 1");
    private static Entidad mcdonalds = new Entidad("McDonalds","Denominacion 2");
    private static Entidad burgerKing = new Entidad("BurgerKing", "Denominacion 3");

    private static Establecimiento sucursal1BBVA = new Establecimiento("Sucursal 1", "Sucursal 1");
    private static Establecimiento sucursal2MC = new Establecimiento("Sucursal 2", "Sucursal 2");
    private static Establecimiento estacion1BK = new Establecimiento("Estacion 1", "Estacion 1");
    private static Establecimiento estacion2MC = new Establecimiento("Estacion 2", "Estacion 2");
    private static Establecimiento estacion3BK = new Establecimiento("Estacion 3", "Estacion 3");

    private static Servicio banioHombreBebe = new Servicio(new Etiqueta("tipoServicio","banio"), new Etiqueta("genero","hombre"), new Etiqueta("edad","bebe"));
    private static Servicio escaleraMolinete = new Servicio(new Etiqueta("tipoServicio","escalera"), new Etiqueta("tipoAcceso","molinete"));
    private static Servicio escaleraMolineteAncho = new Servicio(new Etiqueta("tipoServicio","escalera"), new Etiqueta("tipoAcceso","molinete"), new Etiqueta("ancho","ancho"));
    private static Servicio banioSinGeneroAscensor = new Servicio(new Etiqueta("tipoServicio","banio"), new Etiqueta("tipoAcceso","ascensor"));

    private static  ServicioPrestado servicioPrestado1BBVA = new ServicioPrestado(banioHombreBebe);
    private static ServicioPrestado servicioPrestado2MC = new ServicioPrestado(escaleraMolinete);
    private static ServicioPrestado servicioPrestado3BK = new ServicioPrestado(escaleraMolineteAncho);
    private static ServicioPrestado servicioPrestado4BK = new ServicioPrestado(banioSinGeneroAscensor);

    private static Incidente incidente1BBVA = new Incidente();
    private static Incidente incidente2MC = new Incidente();
    private static Incidente incidente3BK = new Incidente();
    private static Incidente incidente4BK = new Incidente();

    private static Incidente incidente4BK_2 = new Incidente();
    private static Incidente incidente1BBVA_2 = new Incidente();
    private static Incidente incidente6MC = new Incidente();

    private static IncidentePorComunidad incidentePC1_BBVA = new IncidentePorComunidad(incidente1BBVA);
    private static IncidentePorComunidad incidentePC2_MC = new IncidentePorComunidad(incidente2MC);
    private static IncidentePorComunidad incidentePC3_BK = new IncidentePorComunidad(incidente3BK);
    private static IncidentePorComunidad incidentePC4_BK = new IncidentePorComunidad(incidente4BK);
    private static IncidentePorComunidad incidentePC4_BK_2 = new IncidentePorComunidad(incidente4BK_2);
    private static IncidentePorComunidad incidentePC1_BBVA_2 = new IncidentePorComunidad(incidente1BBVA_2);
    private static IncidentePorComunidad incidentePC6_MC = new IncidentePorComunidad(incidente6MC);




    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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


        incidente1BBVA.getServicioPrestados().add(servicioPrestado1BBVA);
        incidente1BBVA_2.getServicioPrestados().add(servicioPrestado1BBVA);

        incidente2MC.getServicioPrestados().add(servicioPrestado2MC);
        incidente6MC.getServicioPrestados().add(servicioPrestado2MC);

        incidente3BK.getServicioPrestados().add(servicioPrestado3BK);
        incidente4BK.getServicioPrestados().add(servicioPrestado4BK);
        incidente4BK_2.getServicioPrestados().add(servicioPrestado4BK);





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
        generadorRanking = new PromedioEntreAperturaYCierre();

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
        Assertions.assertEquals(3, ranking.getEntidades().size());

        Assertions.assertEquals(bbva, ranking.getEntidades().get(0));
        Assertions.assertEquals(210, ranking.puntosDe(bbva));

        Assertions.assertEquals(burgerKing, ranking.getEntidades().get(1));
        Assertions.assertEquals(90, ranking.puntosDe(burgerKing));

        Assertions.assertEquals(mcdonalds, ranking.getEntidades().get(2));
        Assertions.assertEquals(60, ranking.puntosDe(mcdonalds));

    }

    @Test
    @DisplayName("Test MasIncidentesEnSemana")
    public void masIncidentesEnSemana(){
        generadorRanking = new MasIncidentesEnSemana();

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
        Assertions.assertEquals(3, ranking.getEntidades().size());

        Assertions.assertEquals(burgerKing, ranking.getEntidades().get(0));
        Assertions.assertEquals(3, ranking.puntosDe(burgerKing));

        Assertions.assertEquals(mcdonalds, ranking.getEntidades().get(1));
        Assertions.assertEquals(2, ranking.puntosDe(mcdonalds));

        Assertions.assertEquals(bbva, ranking.getEntidades().get(2));
        Assertions.assertEquals(1, ranking.puntosDe(bbva));
    }
}
