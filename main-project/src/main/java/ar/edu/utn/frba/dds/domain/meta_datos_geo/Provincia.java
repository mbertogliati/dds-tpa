package ar.edu.utn.frba.dds.domain.meta_datos_geo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "provincias")
@Getter
@Setter
public class Provincia {
  @Id
  private String id;

  @Column(name = "nombre")
  private String nombre;

  @OneToMany
  @JoinColumn(name = "provincia_id", referencedColumnName = "id")
  private List<Departamento> departamentos;

  public Provincia(){}
  public Provincia(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
}
