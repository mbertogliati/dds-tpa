package ar.edu.utn.frba.dds.modelos.servicios;

import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "serviciosPrestados")
@Getter
@Setter
public class ServicioPrestado {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "servicio_id", referencedColumnName = "id")
  private Servicio servicio;

  @ManyToOne
  @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
  private Establecimiento establecimiento;

  public ServicioPrestado(){}
  public ServicioPrestado(Servicio servicio) {
    this.servicio = servicio;
  }
  public Ubicacion getUbicacion(){
    return establecimiento.getUbicacion();
  }
}
