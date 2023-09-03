package ar.edu.utn.frba.dds.domain.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "denominaciones")
@Getter
@Setter
public class Denominacion {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "descripcion")
  private String descripcion;

  public Denominacion(){}
  public Denominacion(String descripcion){
    this.descripcion = descripcion;
  }

}
