package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.utilidades.Localizacion;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Interes {
  @Getter
  private List<EntidadPrestadora> entidades = new ArrayList<>();
  @Getter
  private List<Servicio> servicios = new ArrayList<>();
  @Getter @Setter
  private Localizacion localizacion;

  public Interes(Localizacion localizacion){
    this.localizacion = localizacion;
  }

  public void agregarServicio(Servicio servicio){
    this.servicios.add(servicio);
  }

  public void eliminarServicio(Servicio servicio){
    this.eliminarServicioPorID(servicio.getId());
  }

  private void eliminarServicioPorID(int id){
    servicios.stream().filter(servicio -> servicio.getId() == id).toList().forEach(servicio -> servicios.remove(servicio));
  }

  public void agregarEntidad(EntidadPrestadora entidad){
    this.entidades.add(entidad);
  }

  public void eliminarEntidad(EntidadPrestadora entidad){
    this.eliminarEntidadPorID(entidad.getId());
  }

  private void eliminarEntidadPorID(int id){
    servicios.stream().filter(entidad -> entidad.getId() == id).toList().forEach(entidad -> entidades.remove(entidad));
  }
}
