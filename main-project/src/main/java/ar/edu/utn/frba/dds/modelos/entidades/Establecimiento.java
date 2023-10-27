package ar.edu.utn.frba.dds.modelos.entidades;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "establecimientos")
@Getter
@Setter

public class Establecimiento extends ModelBase {

  @ManyToOne
  @JoinColumn(name = "denominacion_id", referencedColumnName = "id")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private Denominacion denominacion;

  @Column(name = "nombre")
  private String nombre;

  @OneToMany(mappedBy = "establecimiento", cascade = { CascadeType.ALL })
  private List<ServicioPrestado> serviciosPrestados = new ArrayList<>();

  @Embedded
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private Ubicacion ubicacion;

  @ManyToOne
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
  @Override
  public void setActivo(Boolean valor){
    super.setActivo(valor);
    serviciosPrestados.forEach(sp -> sp.setActivo(valor));
  }

}
