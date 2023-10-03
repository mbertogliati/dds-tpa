package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestIncidente {
  @SerializedName("id")
  public int id;

  @SerializedName("id_establecimiento")
  public String idEstablecimiento;

  @SerializedName("id_servicio_afectado")
  public String idServicioAfectado;

  @SerializedName("fecha_de_apertura")
  public String fechaApertura;

  @SerializedName("fecha_de_cierre")
  public String fechaCierre;

  @SerializedName("id_usuario_de_apertura")
  public String idUsuarioApertura;

  @SerializedName("id_usuario_de_cierre")
  public String idUsuarioCierre;
}
