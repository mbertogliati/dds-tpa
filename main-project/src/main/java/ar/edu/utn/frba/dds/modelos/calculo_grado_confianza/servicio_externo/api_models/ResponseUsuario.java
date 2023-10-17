package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUsuario {
  @SerializedName("id")
  public int id;

  @SerializedName("puntaje_inicial")
  public Float puntajeInicial;

  @SerializedName("puntaje_final")
  public Float puntajeFinal;

  @SerializedName("nivel_de_confianza")
  public String nivelConfianza;

  @SerializedName("recomednacion")
  public String recomendacion;
}
