package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UltimoIntentoFusion {
  public Long idOrganizacion;
  //@JsonSerialize(using = CustomDateSerializer.class) //TODO: implement serializer / deserializer ?
  private LocalDateTime fechaIntento;
}
