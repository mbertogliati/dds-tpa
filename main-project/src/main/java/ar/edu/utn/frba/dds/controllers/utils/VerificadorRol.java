package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import java.util.List;

public class VerificadorRol {
  public static Boolean tienePermiso(Usuario usuario, Permiso permiso){
    return usuario.getRoles().stream().map(Rol::getPermisos).flatMap(List::stream).anyMatch(p -> getRol(p.getDetalles()).equals(permiso));
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

  public static Boolean tienePermiso(Usuario usuario, Comunidad comunidad, Permiso permiso){
    if(usuario.getPersonaAsociada().getMembresias().isEmpty()){
      return false;
    }
    List<Comunidad> comunidades = usuario.getPersonaAsociada().getMembresias().stream().filter(m -> m.getComunidad().getId() == comunidad.getId()).map(Membresia::getComunidad).toList();
    if(comunidades.size() == 0){
      return false;
    }else{
      return comunidades.get(0).getMembresia(usuario.getPersonaAsociada()).getRoles().stream().map(Rol::getPermisos).flatMap(List::stream).anyMatch(p -> getRol(p.getDetalles()).equals(permiso));
    }
  }

  public enum Permiso{
    USER_BASE,
    ADMINISTRAR_USUARIOS,
    ADMINISTRAR_COMUNIDAD
  }
}
