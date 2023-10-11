package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUltimoIntentoFusion {
  @SerializedName("idOrganizacion")
  public Long idOrganizacion;

  @SerializedName("fechaIntento")
  private String fechaIntento;
}
