package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSolicitudFusion {
  @SerializedName("organizacion1")
  private RequestOrganizacion organizacion1;

  @SerializedName("organizacion2")
  private RequestOrganizacion organizacion2;
}
