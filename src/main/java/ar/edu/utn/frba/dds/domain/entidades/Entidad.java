package ar.edu.utn.frba.dds.domain.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
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



  public List<Ubicacion> getUbicaciones() {
    List<Ubicacion> ubicaciones = this.establecimientos.stream().map(e -> e.getUbicacion()).toList();
    return ubicaciones;
  }

  public Entidad(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public List<Establecimiento> establecimientosEnLocNoDisp(Ubicacion ubicacion){
    return this.getEstablecimientosEnLocacion(ubicacion);
    //TODO
    //.stream().filter(est -> est.getServiciosPrestados().stream().anyMatch(serv -> serv.isDisponibilidad() == false)).toList();
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

  private List<Establecimiento> getEstablecimientosEnLocacion(Ubicacion ubicacion){
    List<Establecimiento> listaRetornar = new ArrayList<>();

    if(ubicacion.getProvincia() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getProvincia().getId() == ubicacion.getProvincia().getId()).toList());
    }

    if(ubicacion.getMunicipio() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getMunicipio().getId() == ubicacion.getMunicipio().getId()).toList());
    }

    if(ubicacion.getLocalidad() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getLocalidad().getId() == ubicacion.getLocalidad().getId()).toList());
    }

    return listaRetornar;
  }
}
