package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.utilidades.Rol;
import lombok.Getter;
import lombok.Setter;

public class Usuario {
  @Getter @Setter
  private int id;
  @Getter
  private String username;
  @Getter @Setter
  private String password;
  @Getter @Setter
  private Rol rolPlataforma;

  public Usuario(String username, String password){
    this.username = username;
    this.password = password;
  }
}
