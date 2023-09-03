package integration_tests;

import ar.edu.utn.frba.dds.domain.utilidades.Coordenada;
import ar.edu.utn.frba.dds.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.meta_datos_geo.Municipio;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.ServicioGeoRef;
import java.util.List;

public class IntegrationTestServicioGeoRef {
  public static void main(String args[]) {
    ServicioGeoRef servicioGeoRef = new ServicioGeoRef();
    testObtenerMetadatoGeografico(servicioGeoRef);
    testProvincias(servicioGeoRef);
    testDepartamentosMunicipiosYLocalidadesDeProvincia(servicioGeoRef);
  }

  public static void testObtenerMetadatoGeografico(AdapterProveedorMetadatosGeograficos servicioGeoRef) {
    try {
      System.out.println("Cargando metadatos geográficos con servicioGeoRef.obtenerMetadatoGeografico():");

      //Uri: https://apis.datos.gob.ar/georef/api/ubicacion?lat=-27.2741&lon=-66.7529
      MetadatoGeografico metadato = servicioGeoRef.obtenerMetadatoGeografico(new Coordenada(-27.2741f, -66.7529f));

      if (metadato.getProvincia() != null) {
        System.out.println("\tProvincia: " + metadato.getProvincia().getNombre());
      }

      if (metadato.getMunicipio() != null) {
        System.out.println("\tMunicipio: " + metadato.getMunicipio().getNombre());
      }

      if (metadato.getDepartamento() != null) {
        System.out.println("\tDepartamento: " + metadato.getDepartamento().getNombre());
      }

      if (metadato.getLocalidad() != null) {
        System.out.println("\tLocalidad: " + metadato.getLocalidad().getNombre());
      }
    } catch (Exception ex) {
      System.out.println("Ocurrió una excepción al ejecutar servicioGeoRef.obtenerMetadatoGeografico(): " + ex.getMessage());
    }
  }

  public static void testProvincias(AdapterProveedorMetadatosGeograficos servicioGeoRef) {
    try {
      System.out.println("Cargando listado de provincias con servicioGeoRef.testProvincias():");
      List<Provincia> provincias = servicioGeoRef.provincias();
      provincias.forEach(p -> System.out.println("\tProvincia:" + p.getNombre()));
    } catch (Exception ex) {
      System.out.println("Ocurrió una excepción al ejecutar servicioGeoRef.testProvincias(): " + ex.getMessage());
    }
  }

  public static void testDepartamentosMunicipiosYLocalidadesDeProvincia(AdapterProveedorMetadatosGeograficos servicioGeoRef) {
    try {
      System.out.println("Cargando una provincia:");
      List<Provincia> provincias = servicioGeoRef.provincias();
      Provincia unaProvincia = provincias.get(0);
      System.out.println("\tProvincia:" + unaProvincia.getNombre());

      System.out.println("Cargando listado de municipios con servicioGeoRef.municipiosDeProvincia():");
      List<Municipio> municipios = servicioGeoRef.municipiosDeProvincia(unaProvincia);
      municipios.forEach(p -> System.out.println("\tMunicipio:" + p.getNombre()));

      System.out.println("Cargando listado de departamentos con servicioGeoRef.departamentosDeProvincia():");
      List<Departamento> departamentos = servicioGeoRef.departamentosDeProvincia(unaProvincia);
      departamentos.forEach(d -> System.out.println("\tDepartamento:" + d.getNombre()));

      System.out.println("Cargando listado de localidades con servicioGeoRef.localidadesDeProvincia():");
      List<Localidad> localidades = servicioGeoRef.localidadesDeProvincia(unaProvincia);
      departamentos.forEach(d -> System.out.println("\tLocalidad:" + d.getNombre()));
    } catch (Exception ex) {
      System.out.println("Ocurrió una excepción al ejecutar esta prueba: " + ex.getMessage());
    }
  }
}
