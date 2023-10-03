package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestGradoConfianza {
  @SerializedName("usuarios")
  public List<RequestUsuario> usuarios = new ArrayList<>();

  @SerializedName("incidentes")
  public List<RequestIncidente> incidentes = new ArrayList<>();
}
