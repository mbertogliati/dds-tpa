package ar.edu.utn.frba.dds.domain.comunidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Rol {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToMany
  private List<Permiso> permisos = new ArrayList<>();

  public Rol(){}

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
