package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoConfianza {
  public List<UsuarioEvaluado> usuarios = new ArrayList<>();
  public Float nivel;
}
