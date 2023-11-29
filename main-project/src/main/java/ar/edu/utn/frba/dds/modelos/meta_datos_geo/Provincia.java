package ar.edu.utn.frba.dds.modelos.meta_datos_geo;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "provincias")
@Getter
@Setter
public class Provincia {
  @Id
  @GeneratedValue
  private Integer id;

  @Column(name="id_externo")
  private String idExterno;

  @Column(name = "nombre")
  private String nombre;

  @OneToMany(mappedBy = "provincia")
  @Cascade(CascadeType.ALL)
  private List<Departamento> departamentos;

  public Provincia(){}
  public Provincia(String id, String nombre) {
    this.idExterno = id;
    this.nombre = nombre;
  }
  public Provincia(String id) {
    this.idExterno = id;
  }
}
