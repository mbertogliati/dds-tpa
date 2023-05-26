package ar.edu.utn.frba.dds.domain.entidades;

import java.util.List;
import lombok.Getter;

public class OrganismoControl extends Entidad {
  @Getter
  private int id;
  @Getter
  private List<Entidad> entidades;

  public OrganismoControl(String nombre, Denominacion denominacion) {
    super(nombre, denominacion);
  }

  public void enviarInformacion(){
    //TODO: IMPLEMENTAR ENVIO DE INFORMACION
  }
}