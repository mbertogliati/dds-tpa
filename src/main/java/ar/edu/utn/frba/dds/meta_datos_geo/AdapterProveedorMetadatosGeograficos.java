package ar.edu.utn.frba.dds.meta_datos_geo;

import java.io.IOException;
import java.util.List;

public interface AdapterProveedorMetadatosGeograficos {
  //Este metodo
  MetadatoGeografico obtenerMetadatoGeografico(float latitud, float longitud) throws IOException;
  Provincia obtenerProvincia(float latitud, float longitud) throws IOException;
  Departamento obtenerDepartamento(float latitud, float longitud) throws  IOException;
  Localidad obtenerLocalidad(float latitud, float longitud) throws IOException;
  Municipio obtenerMunicipio(float latitud, float longitud) throws IOException;
  List<Provincia> provincias() throws IOException;
  List<Municipio> municipiosDeProvincia(Provincia provincia) throws IOException;
  List<Departamento> departamentosDeProvincia(Provincia provincia) throws IOException;
  List<Localidad> localidadesDeProvincia(Provincia provincia) throws IOException;

  //TODO: FIJARSE QUE SERVICIOGEOREF UTILIZA PROVINCIA, MUNICIPIO Y LOCALIDAD. NO SE SI ESTÁ BIEN MODULARIZADO
  //TODO: OBTENER LISTADO DE PROVINCIAS Y QUE CADA PROVINCIA TENGA SUS MUNICIPIOS Y QUE CADA MUNICIPIO TENGA SUS LOCALIDADES
}
