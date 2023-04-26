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

  private long ultimoIntentoInicioSesion = 0;
  @Getter
  private int delaySeg = 0;

  private final int factorSegundos = 3;

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

  public void cambiarPassword(String viejaPassword, String nuevaPassword) {
    password.cambiarPassword(viejaPassword,nuevaPassword,username);
  }

  private boolean sePuedeIniciarSesion(){
    return System.currentTimeMillis() - ultimoIntentoInicioSesion > delaySeg* 1000L;
  }

  public boolean iniciarSesion(String password) {
    if(!sePuedeIniciarSesion()){
      System.out.println("Debe esperar " + delaySeg + " segundos para volver a intentar");
      return false;
    }
    ultimoIntentoInicioSesion = System.currentTimeMillis();
    delaySeg = 1;
    if(sesionIniciada || this.password.esCorrecta(password)){
      sesionIniciada = true;
      delaySeg = 0;
      return true;
    }
    else{
      if(delaySeg == 0){
        delaySeg+=factorSegundos;
      }
      else{
        delaySeg*=factorSegundos;
      }
      return false;
    }
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
