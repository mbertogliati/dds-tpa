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
    organizacion.setGradoConfianza(comunidad.getGradoConfianza());

    return organizacion;
  }

  public static Comunidad obtenerComunidad(Organizacion organizacion, Comunidad comunidad1, Comunidad comunidad2, EntityManager entityManager){
    Comunidad comunidad = new Comunidad();

    comunidad.setNombre("Fusión de " + comunidad1.getNombre() + " y " + comunidad2.getNombre());
    comunidad.setDetalle(comunidad1.getDetalle() + " - " + comunidad2.getDetalle());
    comunidad.setGradoConfianza(organizacion.getGradoConfianza());
    comunidad.setActivo(true);

    List<ServicioPrestado> serviciosPrestados = Stream.concat(comunidad1.getServiciosPrestados().stream(),comunidad2.getServiciosPrestados().stream()).toList();
     serviciosPrestados.forEach(sp -> {
       if(comunidad.getServiciosPrestados().stream().noneMatch(s -> s.getId() == sp.getId())){
         comunidad.agregarServicio(sp);
       }
     });

    RolRepositorio repoRol = new RolRepositorio(entityManager);
    List<Membresia> membresias = Stream.concat(comunidad1.getMembresias().stream(),comunidad2.getMembresias().stream()).toList();
    for(Long idPersona : organizacion.getMiembros()){
      List<Membresia> membresiasDePersona = membresias.stream().filter(m -> m.getPersona().getId() == idPersona).toList();
      Rol rolPersona;

      if(membresiasDePersona.stream().anyMatch(m -> m.getRolComunidad().getId() == repoRol.rolAdminComunidad().getId())){
        rolPersona = repoRol.rolAdminComunidad();
      }else{
        rolPersona = repoRol.rolDefaultComunidad();
      }

      comunidad.agregarPersona(membresiasDePersona.get(0).getPersona(), rolPersona);
    }

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
