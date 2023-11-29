package ar.edu.utn.frba.dds.modelos.servicios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipos")
@Getter
@Setter
public class TipoEtiquetas {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "nombre")
  private String nombre;

  public TipoEtiquetas(){}

  public TipoEtiquetas(String nombre){
    this.nombre = nombre;
  }
}
