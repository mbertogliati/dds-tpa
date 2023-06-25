package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.meta_datos_geo.*;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

public class Ubicacion {
  @Getter
  private float latitud;
  @Getter
  private float longitud;
  @Getter
  private Provincia provincia;
  @Getter
  private Municipio municipio;
  @Getter
  private Localidad localidad;
  @Setter
  private AdapterMetadatosGeograficos adapterMetadatosGeograficos;

  public Ubicacion(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
    this.adapterMetadatosGeograficos = null;
    this.localidad = null;
    this.municipio = null;
    this.provincia = null;
  }

  //Constructores de provincia, municipio o localidad para seleccionar de un menú (sin lat/long)
  public Ubicacion(Provincia provincia, Municipio municipio, Localidad localidad) {
    this.provincia = provincia;
    this.municipio = municipio;
    this.localidad = localidad;
  }
  public Ubicacion(Provincia provincia, Municipio municipio) {
    this.provincia = provincia;
    this.municipio = municipio;
  }
  public Ubicacion(Provincia provincia) {
    this.provincia = provincia;
  }

  public void cargarMetadatosGeograficos() throws IOException {
    if (this.adapterMetadatosGeograficos != null) {
      var metadatos = this.adapterMetadatosGeograficos.obtenerMetadatoGeografico(this.latitud, this.longitud);
      this.provincia = metadatos.getProvincia();
      this.municipio = metadatos.getMunicipio();
      this.localidad = metadatos.getLocalidad();
    }
  }

  public void setLatitudLongitud(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public boolean estaCercaDe(Ubicacion ubicacion){
    DistanciaEntrePuntos medidorDistancia = new DistanciaEnMetros();

    //CONSIDERO CERCA A 500 METROS
    return medidorDistancia.distanciaEntre(this, ubicacion) < 500;
  }

  public boolean coincideCon(Ubicacion ubicacion) {
    if(this.localidad == null && ubicacion.localidad == null){//si ambas LOCALIDADES son null

      if(this.municipio == null && ubicacion.municipio == null){//si ambos MUNICIPIOS son null

        if(this.provincia == null || ubicacion.provincia == null){ //si alguna PROVINCIA es null
          return false;
        }else{ //si ninguna PROVINCIA es null
          return this.provincia.getId() == ubicacion.provincia.getId();
        }

      }else{
        if(this.municipio == null || ubicacion.municipio == null){//si un MUNICIPIO es null
          return false;
        }else{//si ningun MUNICIPIO es null
          return this.municipio.getId() == ubicacion.municipio.getId();
        }
      }

    }else{
      if(this.localidad == null || ubicacion.localidad == null){//si solo una LOCALIDAD es null
        return false;
      }else{//si ninguna LOCALIDAD es null
        return this.localidad.getId() == ubicacion.localidad.getId();
      }
    }
  }
}
