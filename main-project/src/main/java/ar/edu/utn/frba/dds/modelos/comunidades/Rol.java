package ar.edu.utn.frba.dds.modelos.comunidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Rol {
  @Id

  private int id;

  @ManyToMany
  @Cascade(CascadeType.ALL)
  private List<Permiso> permisos = new ArrayList<>();

  @Column(name = "nombre")
  private String nombre;

  public Rol(){}

  public Rol(int id){
    this.id=id;
  }

  public void agregarPermiso(Permiso permiso){
    this.permisos.add(permiso);
  }

  public void eliminarPermiso(Permiso permiso){
    this.eliminarPermisoPorID(permiso.getId());
  }

  private void eliminarPermisoPorID(int id){
    permisos.stream().filter(perm -> perm.getId() == id).toList().forEach(perm -> permisos.remove(perm));
  }
}
