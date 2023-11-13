package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import java.util.List;
import java.util.Objects;

public class FiltradorPorComunidad {
  public static List<Provincia> provinciasDeComunidad(Comunidad comunidad, List<Provincia> provincias){
    return provincias.stream().filter(p -> obtenerServiciosPrestados(comunidad).stream().map(sp -> sp.getUbicacion().getMetadato().getProvincia().getId()).toList().contains(p.getId())).toList();
  }

  public static List<Departamento> departamentosDeComunidad(Comunidad comunidad, List<Departamento> departamentos){
    return departamentos.stream().filter(d -> obtenerServiciosPrestados(comunidad).stream().map(sp -> sp.getUbicacion().getMetadato().getDepartamento().getId()).toList().contains(d.getId())).toList();
  }

  public static List<Localidad> localidadesDeComunidad(Comunidad comunidad, List<Localidad> localidades){
    return localidades.stream().filter(l -> obtenerServiciosPrestados(comunidad).stream().map(sp -> sp.getUbicacion().getMetadato().getLocalidad().getId()).toList().contains(l.getId())).toList();
  }

  public static List<Entidad> entidadesDeComunidad(Comunidad comunidad, List<Entidad> entidades){
    return entidades.stream().filter(e -> obtenerServiciosPrestados(comunidad).stream().map(sp -> sp.getEstablecimiento().getEntidad().getId()).toList().contains(e.getId())).toList();
  }

  public static List<Establecimiento> establecimientosDeComunidad(Comunidad comunidad, List<Establecimiento> establecimientos){
    return establecimientos.stream().filter(e -> obtenerServiciosPrestados(comunidad).stream().map(sp -> sp.getEstablecimiento().getId()).toList().contains(e.getId())).toList();
  }

  public static List<ServicioPrestado> serviciosPrestadosDeComunidad(Comunidad comunidad, List<ServicioPrestado> serviciosPrestados){
    return serviciosPrestados.stream().filter(sp -> obtenerServiciosPrestados(comunidad).stream().map(s -> s.getId()).toList().contains(sp.getId())).toList();
  }

  public static List<Provincia> provinciasConIncidentes(Comunidad comunidad, List<Provincia> provincias){
    return provincias.stream().filter(p -> obtenerServiciosPrestadosConIncidentes(comunidad).stream().anyMatch(s -> Objects.equals(s.getUbicacion().getMetadato().getProvincia().getId(), p.getId()))).toList();
  }

  public static List<Departamento> departamentosConIncidentes(Comunidad comunidad, List<Departamento> departamentos){
    return departamentos.stream().filter(d -> obtenerServiciosPrestadosConIncidentes(comunidad).stream().anyMatch(s -> Objects.equals(s.getUbicacion().getMetadato().getDepartamento().getId(), d.getId()))).toList();
  }

  public static List<Localidad> localidadesConIncidentes(Comunidad comunidad, List<Localidad> localidades){
    return localidades.stream().filter(l -> obtenerServiciosPrestadosConIncidentes(comunidad).stream().anyMatch(s -> Objects.equals(s.getUbicacion().getMetadato().getLocalidad().getId(), l.getId()))).toList();
  }

  private static List<ServicioPrestado> obtenerServiciosPrestados(Comunidad comunidad){
    return comunidad.getServiciosPrestados();
  }
  private static List<ServicioPrestado> obtenerServiciosPrestadosConIncidentes(Comunidad comunidad){
    return comunidad.getIncidentes().stream().map(IncidentePorComunidad::getIncidente).flatMap(i -> i.getServiciosAfectados().stream()).toList();
  }
}
