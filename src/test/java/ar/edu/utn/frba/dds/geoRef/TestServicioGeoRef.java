package ar.edu.utn.frba.dds.geoRef;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ar.edu.utn.frba.dds.geoRef.api_models.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import static org.mockito.Mockito.*;

public class TestServicioGeoRef{
  //private GeoRefService geoRefServiceMock;
  //private Call<ResponseUbicacion> responseUbicacionMock;
  //private Call<ListadoDeProvincias> listadoDeProvinciasMock;

  @BeforeEach
  public void init(){
    //this.geoRefServiceMock = mock(GeoRefService.class);
    //this.responseUbicacionMock = (Call<ResponseUbicacion>) mock(Call.class);
    //this.listadoDeProvinciasMock = (Call<ListadoDeProvincias>) mock(Call.class);
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
    Provincia unaProvincia = servicioGeoRef.obtenerProvincia(latitud, longitud);

    //assert
    assertEquals("Buenos Aires", unaProvincia.getNombre());
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
    Localidad unaLocalidad = servicioGeoRef.obtenerLocalidad(latitud, longitud);

    //assert
    assertEquals("Lugano", unaLocalidad.getNombre());
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

    //act
    Municipio unMunicipio = servicioGeoRef.obtenerMunicipio(latitud, longitud);

    //assert
    assertEquals("CABA", unMunicipio.getNombre());
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

    ProvinciaGeoref provinciaGeoref = new ProvinciaGeoref("0", provincia);
    MunicipioGeoref municipioGeoref = new MunicipioGeoref("1", municipio);
    LocalidadGeoref localidadGeoref = new LocalidadGeoref("2", localidad);

    ubicacion.provinciaGeoref = provinciaGeoref;
    ubicacion.municipioGeoref = municipioGeoref;
    ubicacion.localidadGeoref = localidadGeoref;

    ResponseUbicacion responseUbicacion = new ResponseUbicacion();
    responseUbicacion.parametros = parametros;
    responseUbicacion.ubicacion = ubicacion;

    return responseUbicacion;
  }

  private ListadoDeProvincias BuildFakeListadoDeProvincias() {
    ListadoDeProvincias listado = new ListadoDeProvincias();

    List<ProvinciaGeoref> provincias = new ArrayList<ProvinciaGeoref>();

    provincias.add(new ProvinciaGeoref("0", "Buenos Aires"));
    provincias.add(new ProvinciaGeoref("1", "Santa Fe"));
    provincias.add(new ProvinciaGeoref("2", "Corrientes"));

    listado.provincias = provincias;
    listado.cantidad = provincias.size();
    listado.total = provincias.size();
    listado.inicio = 0;
    listado.parametros = new ParametroProvincia();

    return listado;
  }
}
