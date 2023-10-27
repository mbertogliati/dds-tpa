package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza;

import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametroConfianza {
  private List<UsuarioAEvaluar> usuarios = new ArrayList<>();
  private List<IncidentePorComunidad> incidentes = new ArrayList<>();
}
