package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.Organizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.UltimoIntentoFusion;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;

public class ConverterComunidadOrganizacion {
  public static Organizacion obtenerOrganizacion(Comunidad comunidad){
    Organizacion organizacion = new Organizacion();

    organizacion.setIdOrganizacion((long) comunidad.getId());
    organizacion.setServicios(obtenerServicios(comunidad.getServiciosPrestados().stream().map(s -> s.getServicio()).toList()));
    organizacion.setEstablecimientos(obtenerEstablecimientos(comunidad.getServiciosPrestados().stream().map(s -> s.getEstablecimiento()).toList()));
    organizacion.setMiembros(obtenerMiembros(comunidad.getMembresias()));
    organizacion.setUltimosIntentosDeFusion(comunidad.getIntentosFusion().stream().map(i -> new UltimoIntentoFusion((long) i.getComunidad().getId(), i.getUltIntentoFusion())).collect(Collectors.toSet()));
    organizacion.setGradoConfianza(comunidad.getGradoConfianza().doubleValue());

    return organizacion;
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
