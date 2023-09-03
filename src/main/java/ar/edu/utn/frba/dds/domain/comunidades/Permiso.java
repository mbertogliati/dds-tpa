package ar.edu.utn.frba.dds.domain.comunidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permisos")
@Getter
@Setter
public class Permiso {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "detalles")
  private String detalles;
}
