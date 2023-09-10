package ar.edu.utn.frba.dds.meta_datos_geo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ar.edu.utn.frba.dds.domain.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.domain.utilidades.Coordenada;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.GeoRefService;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.ServicioGeoRef;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models.EntidadGeoRef;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models.ListadoDeProvincias;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models.ParametroProvincia;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models.ParametroUbicacion;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models.ResponseUbicacion;
import ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models.UbicacionGeoRef;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import static org.mockito.Mockito.*;

public class TestServicioGeoRef {
  @BeforeEach
  public void init(){
  }

  @Test
  @DisplayName("Se obtiene la provincia correcta al buscar por latitud y longitud")
  public void seObtieneLaProvinciaCorrecta() throws IOException {
    //arrange
    Float latitud = (float) -37;
    Float longitud = (float) -61;

    var geoRefServiceMock = mock(GeoRefService.class);
    var responseUbicacionMock = (Call<ResponseUbicacion>) mock(Call.class);

    when(responseUbicacionMock.execute()).thenReturn(Response.success(this.BuildFakeResponseUbicacion(latitud, longitud, "Buenos Aires", "CABA", "Lugano")));
    when(geoRefServiceMock.ubicacion(latitud, longitud)).thenReturn(responseUbicacionMock);
    ServicioGeoRef servicioGeoRef = new ServicioGeoRef(geoRefServiceMock);

    //act
    Provincia unaProvincia = servicioGeoRef.obtenerProvincia(new Coordenada(latitud, longitud));

    //assert
    assertEquals("Buenos Aires", unaProvincia.getNombre());
    System.out.println(unaProvincia.getNombre());
  }
  @Test
  @DisplayName("Se obtiene la localidad correcta al buscar por latitud y longitud")
  public void seObtieneLaLocalidadCorrecta() throws IOException {

    //arrange
    Float latitud = (float) -37;
    Float longitud = (float) -61;

    var geoRefServiceMock = mock(GeoRefService.class);
    var responseUbicacionMock = (Call<ResponseUbicacion>) mock(Call.class);

    when(responseUbicacionMock.execute()).thenReturn(Response.success(this.BuildFakeResponseUbicacion(latitud, longitud, "Buenos Aires", "CABA", "Lugano")));
    when(geoRefServiceMock.ubicacion(latitud, longitud)).thenReturn(responseUbicacionMock);
    ServicioGeoRef servicioGeoRef = new ServicioGeoRef(geoRefServiceMock);

    //act
    Localidad unaLocalidad = servicioGeoRef.obtenerLocalidad(new Coordenada(latitud, longitud));

    //assert
    assertEquals("Lugano", unaLocalidad.getNombre());
    System.out.println(unaLocalidad.getNombre());
  }
  @Test
  @DisplayName("Se obtiene el municipio correcto al buscar por latitud y longitud")
  public void seObtieneElMunicipioCorrecto() throws IOException {


    //arrange
    Float latitud = (float) -37;
    Float longitud = (float) -61;

    var geoRefServiceMock = mock(GeoRefService.class);
    var responseUbicacionMock = (Call<ResponseUbicacion>) mock(Call.class);

    when(responseUbicacionMock.execute()).thenReturn(Response.success(this.BuildFakeResponseUbicacion(latitud, longitud, "Buenos Aires", "CABA", "Lugano")));
    when(geoRefServiceMock.ubicacion(latitud, longitud)).thenReturn(responseUbicacionMock);
    ServicioGeoRef servicioGeoRef = new ServicioGeoRef(geoRefServiceMock);
  }

  @Test
  @DisplayName("Se obtiene el listado de provincias correctamente")
  public void seObtieneElListadoDeProvinciasCorrecto() throws IOException {
    //arrange
    var geoRefServiceMock = mock(GeoRefService.class);
    var listadoDeProvinciasMock = (Call<ListadoDeProvincias>) mock(Call.class);

    when(listadoDeProvinciasMock.execute()).thenReturn(Response.success(this.BuildFakeListadoDeProvincias()));
    when(geoRefServiceMock.provincias("id, nombre")).thenReturn(listadoDeProvinciasMock);
    ServicioGeoRef servicioGeoRef = new ServicioGeoRef(geoRefServiceMock);

    //act
    List<Provincia> provincias = servicioGeoRef.provincias();

    //assert
    assertNotNull(provincias);
    assertEquals(provincias.size(), 3);
  }

  private ResponseUbicacion BuildFakeResponseUbicacion(Float latitud, Float longitud, String provincia, String municipio, String localidad) {
    ParametroUbicacion parametros = new ParametroUbicacion();

    UbicacionGeoRef ubicacion = new UbicacionGeoRef();
    ubicacion.lat = latitud;
    ubicacion.lon = longitud;

    EntidadGeoRef provinciaGeoref = new EntidadGeoRef("0", provincia);
    EntidadGeoRef municipioGeoref = new EntidadGeoRef("1", municipio);
    EntidadGeoRef localidadGeoref = new EntidadGeoRef("2", localidad);

    ubicacion.provincia = provinciaGeoref;
    ubicacion.municipio = municipioGeoref;
    ubicacion.localidad = localidadGeoref;

    ResponseUbicacion responseUbicacion = new ResponseUbicacion();
    responseUbicacion.parametros = parametros;
    responseUbicacion.ubicacion = ubicacion;

    return responseUbicacion;
  }

  private ListadoDeProvincias BuildFakeListadoDeProvincias() {
    ListadoDeProvincias listado = new ListadoDeProvincias();

    List<EntidadGeoRef> provincias = new ArrayList<EntidadGeoRef>();

    provincias.add(new EntidadGeoRef("0", "Buenos Aires"));
    provincias.add(new EntidadGeoRef("1", "Santa Fe"));
    provincias.add(new EntidadGeoRef("2", "Corrientes"));

    listado.provincias = provincias;
    listado.cantidad = provincias.size();
    listado.total = provincias.size();
    listado.inicio = 0;
    listado.parametros = new ParametroProvincia();

    return listado;
  }
}
