package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.FusionCompletada;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.Organizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.UltimoIntentoFusion;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.repositorios.comunidades.ComunidadRepositorio;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConverterComunidadOrganizacion {
  public static Organizacion obtenerOrganizacion(Comunidad comunidad){
    Organizacion organizacion = new Organizacion();

    organizacion.setIdOrganizacion((long) comunidad.getId());
    organizacion.setServicios(obtenerServicios(comunidad.getServicios()));
    organizacion.setEstablecimientos(obtenerEstablecimientos(comunidad.getIncidentes().stream().map(i -> i.getIncidente().getServiciosAfectados().get(0).getEstablecimiento()).toList()));
    organizacion.setMiembros(obtenerMiembros(comunidad.getMembresias()));
    organizacion.setUltimosIntentosDeFusion(comunidad.getIntentosFusion().stream().map(i -> new UltimoIntentoFusion((long) i.getComunidad().getId(), i.getUltIntentoFusion())).collect(Collectors.toSet()));
    organizacion.setGradoConfianza(comunidad.getGradoConfianza());

    return organizacion;
  }

  public static Comunidad obtenerComunidad(Organizacion organizacion, Comunidad comunidad1, Comunidad comunidad2){
    Comunidad comunidad = new Comunidad();

    comunidad.setNombre("Fusión de " + comunidad1.getNombre() + " y " + comunidad2.getNombre());
    comunidad.setDetalle(comunidad1.getDetalle() + " - " + comunidad2.getDetalle());
    comunidad.setGradoConfianza(organizacion.getGradoConfianza());
    comunidad.setActivo(true);
    comunidad.agregarServiciosDe(comunidad1, comunidad2);
    comunidad.agregarMembresiasDe(comunidad1, comunidad2);
    comunidad.agregarIncidentesDe(comunidad1, comunidad2);

    return comunidad;
  }

  private static Set<Long> obtenerServicios(List<Servicio> servicios){
    Set<Long> ids = new HashSet<>();
    servicios.forEach(s -> ids.add((long) s.getId()));
    return ids;
  }

  private static Set<Long> obtenerEstablecimientos(List<Establecimiento> establecimientos){
    Set<Long> ids = new HashSet<>();
    establecimientos.forEach(s -> ids.add((long) s.getId()));
    return ids;
  }

  private static Set<Long> obtenerMiembros(List<Membresia> membresias){
    Set<Long> ids = new HashSet<>();
    membresias.forEach(s -> ids.add((long) s.getPersona().getId()));
    return ids;
  }
}
