package ar.edu.utn.frba.dds.meta_datos_geo;

import ar.edu.utn.frba.dds.domain.utilidades.Coordenada;
import java.io.IOException;
import java.util.List;

public interface AdapterProveedorMetadatosGeograficos {
  //Este metodo
  MetadatoGeografico obtenerMetadatoGeografico(Coordenada coordenada) throws IOException;
  Provincia obtenerProvincia(Coordenada coordenada) throws IOException;
  Departamento obtenerDepartamento(Coordenada coordenada) throws  IOException;
  Localidad obtenerLocalidad(Coordenada coordenada) throws IOException;
  Municipio obtenerMunicipio(Coordenada coordenada) throws IOException;
  List<Provincia> provincias() throws IOException;
  List<Municipio> municipiosDeProvincia(Provincia provincia) throws IOException;
  List<Departamento> departamentosDeProvincia(Provincia provincia) throws IOException;
  List<Localidad> localidadesDeProvincia(Provincia provincia) throws IOException;

  //TODO: FIJARSE QUE SERVICIOGEOREF UTILIZA PROVINCIA, MUNICIPIO Y LOCALIDAD. NO SE SI ESTÁ BIEN MODULARIZADO
  //TODO: OBTENER LISTADO DE PROVINCIAS Y QUE CADA PROVINCIA TENGA SUS MUNICIPIOS Y QUE CADA MUNICIPIO TENGA SUS LOCALIDADES
}
