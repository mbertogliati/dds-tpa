package ar.edu.utn.frba.dds.domain.users;

import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class Comunidad {
  @Setter
  private int id;
  @Setter
  private String nombre;
  @Setter
  private List<Miembro> miembros;

  public Comunidad() {
    this.id = 0;
    this.nombre = null;
    this.miembros = new ArrayList<Miembro>();
  }

  public Comunidad(int id, String nombre, List<Miembro> miembros) {
    this.id = id;
    this.nombre = nombre;
    this.miembros = miembros;
  }

  public void agregarMiembro(Miembro nuevoMiembro) {
    this.miembros.add(nuevoMiembro);
  }

  public void eliminarMiembro(Miembro miembroAEliminar) {
    this.miembros.remove(miembroAEliminar);
  }

  public boolean esMiembro(Usuario usuario) {
    //TODO: implementar es miembro
    return false;
  }
}
