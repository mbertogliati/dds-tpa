package ar.edu.utn.frba.dds.modelos.servicios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "etiquetas", schema = "public")
@Getter
@Setter
public class Etiqueta {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "tipo_id", referencedColumnName = "id")
  private TipoEtiquetas tipo;

  @Column(name = "valor")
  private String valor;

  public Etiqueta(){}

  public Etiqueta(TipoEtiquetas tipo, String valor){
    this.tipo = tipo;
    this.valor = valor;
  }
}
