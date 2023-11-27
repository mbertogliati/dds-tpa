package ar.edu.utn.frba.dds.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcionGenericaDTO {
  private String id;
  private String descripcion;
  private Boolean seleccionado;
  public OpcionGenericaDTO() {}
  public OpcionGenericaDTO(String id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
    this.seleccionado = false;
  }

  public OpcionGenericaDTO(String id, String descripcion, Boolean seleccionado) {
    this.id = id;
    this.descripcion = descripcion;
    this.seleccionado = seleccionado;
  }
}
