package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUsuario {
  @SerializedName("id")
  public int id;
  @SerializedName("puntaje_inicial")
  public int puntajeInicial;
}
