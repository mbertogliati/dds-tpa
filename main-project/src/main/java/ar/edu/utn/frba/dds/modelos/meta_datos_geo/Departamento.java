package ar.edu.utn.frba.dds.modelos.meta_datos_geo;

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
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "departamentos")
@Getter
@Setter
public class Departamento {
  @Id
  private String id;

  @Column(name = "nombre")
  private String nombre;

  @OneToMany(mappedBy = "departamento")
  private List<Localidad> localidades;

  @ManyToOne
  @JoinColumn(name = "provincia_id", referencedColumnName = "id")
  private Provincia provincia;

  public Departamento(){}

  public Departamento(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Departamento(String id, String nombre, Provincia provincia) {
    this.id = id;
    this.nombre = nombre;
    this.provincia = provincia;
  }
}
