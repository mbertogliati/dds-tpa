package ar.edu.utn.frba.dds.modelos.servicios;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
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
import org.hibernate.annotations.Where;

@Entity
@Table(name = "serviciosPrestados")
@Where(clause = "activo = true")
@Getter
@Setter
public class ServicioPrestado extends ModelBase {
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
