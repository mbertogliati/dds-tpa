package ar.edu.utn.frba.dds.domain.entidades;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Localizacion;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

public class OrganismoControl extends Entidad {
  @Getter
  private int id;
  @Getter
  private List<Entidad> entidades;
  @Setter
  private Ubicacion ubicacion;

  public OrganismoControl(String nombre, Denominacion denominacion) {
    super(nombre, denominacion);
  }

  @Override
  public List<Localizacion> getLocalizaciones() {
    List<Localizacion> lista = new ArrayList<>();
    lista.add(this.ubicacion.getDepartamento());
    lista.add(this.ubicacion.getProvincia());
    lista.add(this.ubicacion.getMunicipio());
    return lista;
  }

  public void enviarInformacion(){
    //TODO: IMPLEMENTAR ENVIO DE INFORMACION
  }
}
