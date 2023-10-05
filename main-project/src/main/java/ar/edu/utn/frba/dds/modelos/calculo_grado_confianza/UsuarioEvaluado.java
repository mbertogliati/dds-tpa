package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioEvaluado {
  public int id;
  public int puntajeInicial;
  public int puntajeFinal;
  public String nivelConfianza;
  public String recomendacion;
}
