package ar.edu.utn.frba.dds.domain.users;

import lombok.Setter;

public class Usuario {
  @Setter
  private String nombre;
  @Setter
  private String apellido;
  @Setter
  private String email;
  @Setter
  private String password;
  @Setter
  private RolPlataforma rol;

  public Usuario() {
    this.nombre = null;
    this.apellido = null;
    this.email = null;
    this.password = null;
    this.rol = null;
  }

  public Usuario(String nombre, String apellido, String email, String password, RolPlataforma rol) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.password = password;
    this.rol = rol;
  }

  public boolean validarPassword() {
    //TODO: implementar validar password
    return false;
  }

  public void registrar() {
    //TODO: implementar registrar
  }

  public void suscribirAComunidad(Comunidad comunidad) {
    //TODO: implementar suscribir a comunidad
  }

  public void desuscribirAComunidad(Comunidad comunidad) {
    //TODO: desuscribir a comunidad
  }
}
