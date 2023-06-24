package integration_tests;

import ar.edu.utn.frba.dds.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.ServicioGeoRef;

public class TestServicioGeoRef {

  public static void main(String args[]) {
    testObtenerMetadatoGeografico();
  }

  public static void testObtenerMetadatoGeografico() {
    try {
      System.out.println("Cargando metadatos geográficos con servicioGeoRef.obtenerMetadatoGeografico():");

      ServicioGeoRef servicioGeoRef = new ServicioGeoRef();

      //Uri: https://apis.datos.gob.ar/georef/api/ubicacion?lat=-27.2741&lon=-66.7529
      MetadatoGeografico metadato = servicioGeoRef.obtenerMetadatoGeografico(-27.2741f, -66.7529f);

      if (metadato.getProvincia() != null) {
        System.out.println("Provincia: " + metadato.getProvincia().getNombre());
      }

      if (metadato.getMunicipio() != null) {
        System.out.println("Municipio: " + metadato.getMunicipio().getNombre());
      }

      if (metadato.getLocalidad() != null) {
        System.out.println("Localidad: " + metadato.getLocalidad().getNombre());
      }
    } catch (Exception ex) {
      System.out.println("Ocurrió una excepción al ejecutar servicioGeoRef.obtenerMetadatoGeografico(): " + ex.getMessage());
    }
  }
}
