package ar.edu.utn.frba.dds.domain.entidades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

//TODO: Decidir si necesitamos el atributo establecimientos y evitar la lógica de listas
public class Entidad {
  @Getter @Setter
  private int id;
  @Getter
  private List<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
  @Getter
  private String nombre;
  @Getter
  private Denominacion denominacion;
  @Getter @Setter
  private Ubicacion ubicacion;

  public Entidad(String nombre, String denominacion) {
    this.nombre = nombre;
    this.denominacion = new Denominacion(denominacion);
  }

  public Entidad(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public void agregarEstablecimiento(Establecimiento establecimiento, int posicion){
    this.establecimientos.add(posicion, establecimiento);
  }

  public List<Ubicacion> getUbicaciones(){
    return this.establecimientos.stream().map(e -> e.getUbicacion()).toList();
  }

  public void agregarEstablecimiento(Establecimiento establecimiento){
    establecimiento.setEntidad(this);
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

  private List<Establecimiento> getEstablecimientosEnLocacion(Ubicacion ubicacion) {
    List<Establecimiento> listaRetornar = new ArrayList<>();

    if(ubicacion.getMetadato().getProvincia() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getUbicacion().getMetadato().getProvincia().getId() == ubicacion.getMetadato().getProvincia().getId()).toList());
    }

    if(ubicacion.getMetadato().getMunicipio() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getUbicacion().getMetadato().getMunicipio().getId() == ubicacion.getMetadato().getMunicipio().getId()).toList());
    }

    if(ubicacion.getMetadato().getLocalidad() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getUbicacion().getMetadato().getLocalidad().getId() == ubicacion.getMetadato().getLocalidad().getId()).toList());
    }

    return listaRetornar;
  }
}
