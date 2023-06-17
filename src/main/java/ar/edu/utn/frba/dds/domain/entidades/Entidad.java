package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.utilidades.Localizacion;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Entidad {
  @Getter @Setter
  private int id;
  @Getter
  private List<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
  @Getter
  private String nombre;
  @Getter
  private Denominacion denominacion;

  public List<Localizacion> getLocalizaciones() {
    List<Localizacion> localizaciones = new ArrayList<Localizacion>();
    this.establecimientos.stream().forEach(e -> {
      localizaciones.addAll(e.getUbicacion().getLocalizaciones());
    });
    return localizaciones;
  }

  public Entidad(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public List<Establecimiento> establecimientosEnLocNoDisp(Localizacion loc){
    return this.getEstablecimientosEnLocacion(loc).stream().filter(est -> est.getServiciosPrestados().stream().anyMatch(serv -> serv.isDisponibilidad() == false)).toList();
  }

  public void agregarEstablecimiento(Establecimiento establecimiento, int posicion){
    this.establecimientos.add(posicion, establecimiento);
  }

  public void agregarEstablecimiento(Establecimiento establecimiento){
    this.establecimientos.add(establecimiento);
  }

  public void eliminarEstablecimiento(Establecimiento establecimiento){
    this.eliminarEstablecimientoPorID(establecimiento.getId());
  }

  private void eliminarEstablecimientoPorID(int id){
    this.establecimientos.stream().filter(establecimiento -> establecimiento.getId() == id).toList().forEach(est -> establecimientos.remove(est));
  }

  public Establecimiento getPrimero(){
    return establecimientos.get(0);
  }

  public Establecimiento getUltimo(){
    return establecimientos.get(establecimientos.size() - 1);
  }

  private List<Establecimiento> getEstablecimientosEnLocacion(Localizacion localizacion){
    List<Establecimiento> listaRetornar = new ArrayList<>();

    switch (localizacion.getTipo()){
      case MUNICIPIO -> listaRetornar = this.establecimientos.stream().filter(est -> est.getUbicacion().getMunicipio() == localizacion).toList();
      case PROVINCIA -> listaRetornar = this.establecimientos.stream().filter(est -> est.getUbicacion().getProvincia() == localizacion).toList();
      case DEPARTAMENTO -> listaRetornar = this.establecimientos.stream().filter(est -> est.getUbicacion().getDepartamento() == localizacion).toList();
    }

    return listaRetornar;
  }
}
