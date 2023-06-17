package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Rol {
  @Getter
  private int id;
  @Getter
  private List<Permiso> permisos = new ArrayList<>();

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
