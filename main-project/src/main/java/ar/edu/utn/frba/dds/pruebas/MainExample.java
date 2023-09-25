package ar.edu.utn.frba.dds.pruebas;

import ar.edu.utn.frba.dds.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.rankings.GeneradorRanking;
import ar.edu.utn.frba.dds.modelos.rankings.MasIncidentes;
import ar.edu.utn.frba.dds.modelos.rankings.PromedioEntreAperturaYCierre;
import ar.edu.utn.frba.dds.modelos.rankings.Ranking;
import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import ar.edu.utn.frba.dds.repositorios.rankings.RankingRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainExample implements WithSimplePersistenceUnit{
  public static void main(String[] args) {
  }
}
