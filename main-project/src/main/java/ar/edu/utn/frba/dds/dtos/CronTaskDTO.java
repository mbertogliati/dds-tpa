package ar.edu.utn.frba.dds.dtos;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CronTaskDTO {
  private Long id;
  private String nombre;
  private String comando;
  private String tipo;
  private Boolean habilitado;
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaModificacion;

  public CronTaskDTO(){}
}
