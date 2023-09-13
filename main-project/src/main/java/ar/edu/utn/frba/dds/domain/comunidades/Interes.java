package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Interes {
  @ManyToMany
  @JoinTable(name = "interes_entidades")
  private List<Entidad> entidades = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "interes_serviciosPrestados")
  private List<ServicioPrestado> servicios = new ArrayList<>();

  public boolean servicioPrestadoEsDeInteres(ServicioPrestado servicioPrestado){
    return servicios.contains(servicioPrestado.getServicio())
            && entidades.contains(servicioPrestado.getEstablecimiento().getEntidad());

  }

  public void agregarServicio(ServicioPrestado servicio){
    this.servicios.add(servicio);
  }

  public void eliminarServicio(ServicioPrestado servicio){
    this.eliminarServicioPorID(servicio.getId());
  }

  private void eliminarServicioPorID(int id){
    servicios.stream().filter(servicio -> servicio.getId() == id).toList().forEach(servicio -> servicios.remove(servicio));
  }

  public void agregarEntidad(Entidad entidad){
    this.entidades.add(entidad);
  }

  public void eliminarEntidad(Entidad entidad){
    this.eliminarEntidadPorID(entidad.getId());
  }

  private void eliminarEntidadPorID(int id){
    servicios.stream().filter(entidad -> entidad.getId() == id).toList().forEach(entidad -> entidades.remove(entidad));
  }
}
