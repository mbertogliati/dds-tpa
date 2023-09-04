package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.meta_datos_geo.Municipio;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "establecimientos")
@Getter
@Setter
public class Establecimiento {
  @Id
  @GeneratedValue
  private int id;

  @Transient
  private Denominacion denominacion;

  @Column(name = "nombre")
  private String nombre;

  @OneToMany(mappedBy = "establecimiento")
  private List<ServicioPrestado> serviciosPrestados = new ArrayList<>();

  @Transient
  private Ubicacion ubicacion;

  @ManyToOne
  @JoinColumn(name = "entidad_id", referencedColumnName = "id")
  private Entidad entidad;

  public Establecimiento(){}

  public Establecimiento(String nombre, String denominacion){
    this.nombre = nombre;
    this.denominacion = new Denominacion(denominacion);
  }

  public Establecimiento(String nombre, Denominacion denominacion){
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public ServicioPrestado agregarServicio(Servicio servicio){
    ServicioPrestado servicioPrestado = new ServicioPrestado(servicio);
    servicioPrestado.setEstablecimiento(this);
    this.serviciosPrestados.add(servicioPrestado);
    return servicioPrestado;
  }

  public void eliminarServicioPrestado(ServicioPrestado servicioPrestado){
    this.serviciosPrestados.removeIf(servicioPres -> servicioPres.getId() == servicioPrestado.getId());
  }

  public void eliminarServicio(Servicio servicio){
    this.serviciosPrestados.removeIf(servicioPres -> servicioPres.getServicio().getId() == servicio.getId());
  }

  public List<ServicioPrestado> serviciosPrestadosDelServicio(Servicio servicio){
    return this.serviciosPrestados.stream().filter(servicioPrestado -> servicioPrestado.getServicio().getId() == servicio.getId()).toList();
  }


}
