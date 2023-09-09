package ar.edu.utn.frba.domain.entidades;

import ar.edu.utn.frba.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UltimoIntentoFusion {
  public Long organizacionId;
  @JsonSerialize(using = CustomDateSerializer.class)
  private LocalDateTime fechaIntento;
}
