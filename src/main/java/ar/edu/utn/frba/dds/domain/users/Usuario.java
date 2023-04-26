package ar.edu.utn.frba.dds.domain.users;
import ar.edu.utn.frba.dds.domain.security.Password;

import lombok.Setter;
import lombok.Getter;

public class Usuario {
  @Getter
  private String username;
  private String usuario;
  @Setter
  private String nombre;
  @Setter
  private String apellido;
  @Setter
  private String email;

  private final Password password;
  @Setter
  private RolPlataforma rol;

  @Getter
  private boolean sesionIniciada = false;

  public Usuario() {
    this.username = null;
    this.nombre = null;
    this.apellido = null;
    this.email = null;
    this.password = null;
    this.rol = null;
  }

  public Usuario(String username, String usuario, String password) {
    this.username = username;
    this.usuario = usuario;
    this.password = new Password(password, usuario);
  }

  public void cambiarPassword(String viejaPassword, String nuevaPassword,String usuario) {
    password.cambiarPassword(viejaPassword,nuevaPassword,usuario);
  }

  public boolean iniciarSesion(String password) {
    return sesionIniciada = this.password.esValida(password);
  }

  public void cerrarSesion() {
    sesionIniciada = false;
  }

  public void suscribirAComunidad(Comunidad comunidad) {
    if(this.sesionIniciada){
      comunidad.agregarMiembro(this);
    }
  }

  public void desuscribirAComunidad(Comunidad comunidad) {
    if(this.sesionIniciada){
      comunidad.eliminarMiembro(this);
    }
  }
}
