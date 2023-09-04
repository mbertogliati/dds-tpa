package ar.edu.utn.frba.dds.meta_datos_geo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "departamentos")
@Getter
@Setter
public class Departamento {
  @Id
  private String id;

  @Column(name = "nombre")
  private String nombre;

  @OneToMany
  @JoinColumn(name = "departamento_id", referencedColumnName = "id")
  private List<Localidad> localidades;

  public Departamento(){}

  public Departamento(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
}
