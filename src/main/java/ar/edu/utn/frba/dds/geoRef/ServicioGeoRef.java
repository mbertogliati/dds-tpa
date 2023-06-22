package ar.edu.utn.frba.dds.geoRef;


import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoRef {
  private static ServicioGeoRef instancia;
  private static final String urlApi = "https://apis.datos.gob.ar/georef/api/";
  private Retrofit retrofit;

  private ServicioGeoRef(){
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static ServicioGeoRef instancia(){
    if(instancia == null){
      instancia = new ServicioGeoRef();
    }
    return instancia;
  }

  public Provincia provincia(float latitud, float longitud) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(latitud, longitud);

    return new Provincia(Integer.parseInt(response.ubicacion.provinciaGeoref.id), response.ubicacion.provinciaGeoref.nombre);
  }
  public Localidad localidad(float latitud, float longitud) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(latitud, longitud);

    return new Localidad(Integer.parseInt(response.ubicacion.localidadGeoref.id), response.ubicacion.localidadGeoref.nombre);
  }
  public Municipio municipio(float latitud, float longitud) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(latitud, longitud);

    return new Municipio(Integer.parseInt(response.ubicacion.municipioGeoref.id), response.ubicacion.municipioGeoref.nombre);
  }

  private ResponseUbicacion getResponseUbicacion(float latitud, float longitud) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ResponseUbicacion> requestBuscada = geoRefService.ubicacion(latitud, longitud);
    Response<ResponseUbicacion> responseBuscada = requestBuscada.execute();
    return responseBuscada.body();
  }

  public List<Provincia> provincias() throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeProvincias> requestBuscada = geoRefService.provincias("id, nombre");
    Response<ListadoDeProvincias> responseBuscada = requestBuscada.execute();
    return responseBuscada.body().provinciaGeorefs.stream().map(prov -> new Provincia(Integer.parseInt(prov.id), prov.nombre)).toList();
  }
  public List<Municipio> municipiosDeProvincia(Provincia provincia) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeMunicipios> requestBuscada = geoRefService.municipios(String.valueOf(provincia.getId()), "id, nombre");
    Response<ListadoDeMunicipios> responseBuscada = requestBuscada.execute();
    ListadoDeMunicipios lista = responseBuscada.body();

    return lista.municipios.stream().map(mun -> new Municipio(Integer.parseInt(mun.id), mun.nombre)).toList();
  }
  public List<Localidad> localidadesDeMunicipio(Municipio municipio) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeMunicipios> requestBuscada = geoRefService.municipios(String.valueOf(municipio.getId()), "id, nombre");
    Response<ListadoDeMunicipios> responseBuscada = requestBuscada.execute();
    ListadoDeMunicipios lista = responseBuscada.body();

    return lista.municipios.stream().map(mun -> new Localidad(Integer.parseInt(mun.id), mun.nombre)).toList();
  }
}
