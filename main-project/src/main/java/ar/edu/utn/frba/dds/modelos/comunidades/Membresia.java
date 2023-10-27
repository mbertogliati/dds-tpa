package ar.edu.utn.frba.dds.modelos.comunidades;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "membresias")

@Getter
@Setter
public class Membresia extends ModelBase {
  @ManyToOne
  private Persona persona;

  @ManyToOne
  private Comunidad comunidad;

  @ManyToOne
  @JoinColumn(name = "rolComunidad_id", referencedColumnName = "id")
  private Rol rolComunidad;

  @ManyToMany
  private List<ServicioPrestado> serviciosObservados;

  public Membresia(){}

  public Membresia(Comunidad comunidad, Persona persona){
    this.persona = persona;
    this.comunidad = comunidad;
    serviciosObservados = new ArrayList<>();
  }
  public Membresia(Comunidad comunidad, Persona persona, Rol rol){
    this.persona = persona;
    this.comunidad = comunidad;
    this.rolComunidad = rol;
    serviciosObservados = new ArrayList<>();
  }

  public void agregarServicioObservado(ServicioPrestado servicioPrestado){
    this.serviciosObservados.add(servicioPrestado);
  }

  public void agregarServicioAfectado(ServicioPrestado servicioPrestado){
    if(this.serviciosObservados.contains(servicioPrestado)){
      this.serviciosObservados.remove(servicioPrestado);
    }
  }

  public boolean estaAfectado(ServicioPrestado servicioPrestado){
    return !serviciosObservados.contains(servicioPrestado);
  }

}
