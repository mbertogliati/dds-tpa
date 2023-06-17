package ar.edu.utn.frba.dds.domain.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.Localizacion;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

public class ControlEntidades {
  @Getter @Setter
  private Persona informado;
  @Getter @Setter
  private int id;
  @Getter
  private List<Entidad> entidades;
  @Setter
  private Ubicacion ubicacion;
  @Getter
  private String nombre;
  @Getter
  private Denominacion denominacion;

  public ControlEntidades(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
    this.entidades = new ArrayList<Entidad>();
  }

  public List<Localizacion> getLocalizaciones() {
    return this.ubicacion.getLocalizaciones();
  }

  public void enviarInformacion(){
    //TODO: IMPLEMENTAR ENVIO DE INFORMACION
  }
}
