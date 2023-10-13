package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseFusionCompletada {
  @SerializedName("idOrganizacion1")
  private Long idOrganizacion1;

  @SerializedName("idOrganizacion2")
  private Long idOrganizacion2;

  @SerializedName("organizacionFusionada")
  private RequestOrganizacion organizacionFusionada;
}
