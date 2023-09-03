package ar.edu.utn.frba.dds.domain.servicios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "etiquetas")
@Getter
@Setter
public class Etiqueta {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "tipo")
  private String tipo;

  @Column(name = "valor")
  private String valor;

  public Etiqueta(){}

  public Etiqueta(String tipo, String valor){
    this.tipo = tipo;
    this.valor = valor;
  }
}
