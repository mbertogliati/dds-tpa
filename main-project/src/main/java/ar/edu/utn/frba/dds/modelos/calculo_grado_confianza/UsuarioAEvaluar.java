package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza;

import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAEvaluar {
  public Usuario usuario;
  public Float puntajeInicial;

  public UsuarioAEvaluar() {
    this.usuario = new Usuario();
    this.puntajeInicial = 0f;
  }

  public UsuarioAEvaluar(Usuario usuario) {
    this.usuario = usuario;
    this.puntajeInicial = 0f;
  }

  public UsuarioAEvaluar(Usuario usuario, Float puntajeInicial) {
    this.usuario = usuario;
    this.puntajeInicial = puntajeInicial;
  }
}
