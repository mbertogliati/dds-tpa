package ar.edu.utn.frba.dds.domain.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.geoRef.Localidad;
import ar.edu.utn.frba.dds.geoRef.Municipio;
import ar.edu.utn.frba.dds.geoRef.Provincia;
import lombok.Getter;
import lombok.Setter;

public class ControlEntidades {
  @Getter @Setter
  private Persona informado;
  @Getter @Setter
  private int id;
  @Getter
  private List<Entidad> entidades;
  @Setter @Getter
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

  public Provincia getProvincia(){
    return this.ubicacion.getProvincia();
  }

  public Municipio getMunicipio(){
    return this.ubicacion.getMunicipio();
  }

  public Localidad getLocalidad(){
    return this.ubicacion.getLocalidad();
  }

  public void enviarInformacion(){
    //TODO: IMPLEMENTAR ENVIO DE INFORMACION
  }
}
