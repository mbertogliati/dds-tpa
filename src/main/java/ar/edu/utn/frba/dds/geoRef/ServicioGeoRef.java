package ar.edu.utn.frba.dds.geoRef;

import ar.edu.utn.frba.dds.geoRef.api_models.ListadoDeLocalidades;
import ar.edu.utn.frba.dds.geoRef.api_models.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.geoRef.api_models.ListadoDeProvincias;
import ar.edu.utn.frba.dds.geoRef.api_models.ResponseUbicacion;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoRef implements AdapterMetadatosGeograficos {
  private static ServicioGeoRef instancia;
  private static final String urlApi = "https://apis.datos.gob.ar/georef/api/";
  private final GeoRefService geoRefService;
  public ServicioGeoRef() {
    Retrofit retrofitBuilder = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    this.geoRefService = retrofitBuilder.create(GeoRefService.class);
  }

  public ServicioGeoRef(GeoRefService geoRefService) {
    this.geoRefService = geoRefService;
  }
  public static ServicioGeoRef instancia() {
    if(instancia == null) {
      instancia = new ServicioGeoRef();
    }
    return instancia;
  }

  public static ServicioGeoRef instancia(GeoRefService geoRefService) {
    if(instancia == null) {
      instancia = new ServicioGeoRef(geoRefService);
    }
    return instancia;
  }

  public MetadatoGeografico obtenerMetadatoGeografico(float latitud, float longitud) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(latitud, longitud);
    return new MetadatoGeografico(
        new Provincia(Integer.parseInt(response.ubicacion.provinciaGeoref.id), response.ubicacion.provinciaGeoref.nombre),
        new Municipio(Integer.parseInt(response.ubicacion.municipioGeoref.id), response.ubicacion.municipioGeoref.nombre),
        new Localidad(Integer.parseInt(response.ubicacion.localidadGeoref.id), response.ubicacion.localidadGeoref.nombre)
    );
  }

  public Provincia obtenerProvincia(float latitud, float longitud) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(latitud, longitud);
    return new Provincia(Integer.parseInt(response.ubicacion.provinciaGeoref.id), response.ubicacion.provinciaGeoref.nombre);
  }
  public Localidad obtenerLocalidad(float latitud, float longitud) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(latitud, longitud);
    return new Localidad(Integer.parseInt(response.ubicacion.localidadGeoref.id), response.ubicacion.localidadGeoref.nombre);
  }
  public Municipio obtenerMunicipio(float latitud, float longitud) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(latitud, longitud);
    return new Municipio(Integer.parseInt(response.ubicacion.municipioGeoref.id), response.ubicacion.municipioGeoref.nombre);
  }

  private ResponseUbicacion getResponseUbicacion(float latitud, float longitud) throws IOException {
    Call<ResponseUbicacion> requestBuscada = this.geoRefService.ubicacion(latitud, longitud);
    Response<ResponseUbicacion> responseBuscada = requestBuscada.execute();
    return responseBuscada.body();
  }

  public List<Provincia> provincias() throws IOException {
    Call<ListadoDeProvincias> requestBuscada = this.geoRefService.provincias("id, nombre");
    Response<ListadoDeProvincias> responseBuscada = requestBuscada.execute();
    return responseBuscada.body().provincias.stream().map(prov -> new Provincia(Integer.parseInt(prov.id), prov.nombre)).toList();
  }
  public List<Municipio> municipiosDeProvincia(Provincia provincia) throws IOException {
    Call<ListadoDeMunicipios> requestBuscada = this.geoRefService.municipios(String.valueOf(provincia.getId()), "id, nombre");
    Response<ListadoDeMunicipios> responseBuscada = requestBuscada.execute();
    ListadoDeMunicipios lista = responseBuscada.body();

    return lista.municipios.stream().map(mun -> new Municipio(Integer.parseInt(mun.id), mun.nombre)).toList();
  }
  public List<Localidad> localidadesDeMunicipio(Municipio municipio) throws IOException {
    Call<ListadoDeLocalidades> requestBuscada = this.geoRefService.departamentos(String.valueOf(municipio.getId()), "id, nombre");
    Response<ListadoDeLocalidades> responseBuscada = requestBuscada.execute();
    ListadoDeLocalidades lista = responseBuscada.body();

    return lista.localidades.stream().map(mun -> new Localidad(Integer.parseInt(mun.id), mun.nombre)).toList();
  }
}
