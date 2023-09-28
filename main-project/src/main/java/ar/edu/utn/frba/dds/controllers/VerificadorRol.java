package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;

public class VerificadorRol {
  public static Boolean tieneRol(Usuario usuario, Permiso permiso){
    return usuario.getRolPlataforma().getPermisos().stream().anyMatch(p -> getRol(p.getDetalles()).equals(permiso));
  }

  private static Permiso getRol(String detalle) {
    switch(detalle){
      case "administrarUsuarios":
        return Permiso.ADMINISTRAR_USUARIOS;

      default:
        return Permiso.USER_BASE;
    }
  }

  public enum Permiso{
    USER_BASE,
    ADMINISTRAR_USUARIOS
  }
}
