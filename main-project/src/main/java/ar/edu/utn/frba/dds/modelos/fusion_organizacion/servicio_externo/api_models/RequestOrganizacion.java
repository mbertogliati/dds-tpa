package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOrganizacion {
  @SerializedName("idOrganizacion")
  private Long idOrganizacion;

  @SerializedName("establecimientos")
  private Set<Long> establecimientos = new HashSet<>();

  @SerializedName("servicios")
  private Set<Long> servicios = new HashSet<>();

  @SerializedName("gradoConfianza")
  private Double gradoConfianza;

  @SerializedName("miembros")
  private Set<Long> miembros = new HashSet<>();

  @SerializedName("ultimosIntentosDeFusion")
  private Set<RequestUltimoIntentoFusion> ultimosIntentosDeFusion = new HashSet<>();
}
