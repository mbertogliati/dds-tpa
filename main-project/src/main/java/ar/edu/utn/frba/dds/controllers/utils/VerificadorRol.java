package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import java.util.List;

public class VerificadorRol {
  public static Boolean tieneRol(Usuario usuario, Permiso permiso){
    return usuario.getRolPlataforma().getPermisos().stream().anyMatch(p -> getRol(p.getDetalles()).equals(permiso));
  }

  private static Permiso getRol(String detalle) {
    switch(detalle){
      case "administrarUsuarios":
        return Permiso.ADMINISTRAR_USUARIOS;
      case "administrarComunidad":
        return Permiso.ADMINISTRAR_COMUNIDAD;
      default:
        return Permiso.USER_BASE;
    }
  }

  public static Boolean tieneRol(Usuario usuario, Comunidad comunidad, Permiso permiso){
    if(usuario.getPersonaAsociada().getMembresias().isEmpty()){
      return false;
    }
    List<Comunidad> comunidades = usuario.getPersonaAsociada().getMembresias().stream().filter(m -> m.getComunidad().getId() == comunidad.getId()).map(m -> m.getComunidad()).toList();
    if(comunidades.size() == 0){
      return false;
    }else{
      return comunidades.get(0).getMembresia(usuario.getPersonaAsociada()).getRolComunidad().getPermisos().stream().anyMatch(p -> getRol(p.getDetalles()).equals(permiso));
    }
  }

  public enum Permiso{
    USER_BASE,
    ADMINISTRAR_USUARIOS,
    ADMINISTRAR_COMUNIDAD
  }
}
