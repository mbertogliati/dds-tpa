package ar.edu.utn.frba.dds.domain.users;

import ar.edu.utn.frba.dds.domain.services.Estacion;
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

  public void agregarMiembro(Usuario nuevoUsuario) {
    Miembro nuevoMiembro = new Miembro(nuevoUsuario, this, new RolComunidad(0, "Miembro"));
    this.miembros.add(nuevoMiembro);
  }

  public void eliminarMiembro(String username) {
    for(Miembro miembroActual : this.miembros){
      if(miembroActual.getUsuario().getUsername() == username){
        this.miembros.remove(miembroActual);
      }
    }
  }
  public void eliminarMiembro(Usuario usuario) {
    this.eliminarMiembro(usuario.getUsername());
  }

  public boolean esMiembro(String username){
    for(Miembro miembroActual : this.miembros){
      if(miembroActual.getUsuario().getUsername() == username){
        return true;
      }
    }
    return false;
  }

  public boolean esMiembro(Usuario usuario) { return this.esMiembro(usuario.getUsername()); }
}
