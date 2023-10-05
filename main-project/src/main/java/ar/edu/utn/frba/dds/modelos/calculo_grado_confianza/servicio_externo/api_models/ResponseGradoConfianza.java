package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGradoConfianza {

  @SerializedName("usuarios_output")
  public List<ResponseUsuario> usuarios;

  @SerializedName("nivel_de_confianza")
  public Float nivel;
}
