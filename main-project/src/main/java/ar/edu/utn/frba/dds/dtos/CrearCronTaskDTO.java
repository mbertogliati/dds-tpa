package ar.edu.utn.frba.dds.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearCronTaskDTO {
  private List<OpcionGenericaDTO> tipos;
  private List<OpcionGenericaDTO> comandos;
  private List<OpcionGenericaDTO> dias;
  public CrearCronTaskDTO() {
    this.tipos = new ArrayList<>();
    this.comandos = new ArrayList<>();
    this.dias = new ArrayList<>();
  }
}
