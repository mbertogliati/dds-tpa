package ar.edu.utn.frba.dds.geoRef;

import static ar.edu.utn.frba.dds.domain.utilidades.TipoLocalizacion.DEPARTAMENTO;
import static ar.edu.utn.frba.dds.domain.utilidades.TipoLocalizacion.MUNICIPIO;
import static ar.edu.utn.frba.dds.domain.utilidades.TipoLocalizacion.PROVINCIA;

import ar.edu.utn.frba.dds.domain.utilidades.Localizacion;
import ar.edu.utn.frba.dds.domain.utilidades.TipoLocalizacion;
import java.io.IOException;
import java.util.ArrayList;
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

  public Localizacion provincia(float latitud, float longitud) throws IOException {
    return this.buscar(latitud, longitud, PROVINCIA);
  }
  public Localizacion departamento(float latitud, float longitud) throws IOException {
    return this.buscar(latitud, longitud, DEPARTAMENTO);
  }
  public Localizacion municipio(float latitud, float longitud) throws IOException {
    return this.buscar(latitud, longitud, MUNICIPIO);
  }
  private Localizacion buscar(float latitud, float longitud, TipoLocalizacion tipo) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ResponseUbicacion> requestBuscada = geoRefService.ubicacion(latitud, longitud);
    Response<ResponseUbicacion> responseBuscada = requestBuscada.execute();

    return this.generarLocalizacionUbi(responseBuscada.body()).stream()
        .filter(loc -> loc.getTipo() == tipo)
        .findFirst()
        .orElse(null);
  }

  public List<Localizacion> ubicacion(float latitud, float longitud) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ResponseUbicacion> requestBuscada = geoRefService.ubicacion(latitud, longitud);
    Response<ResponseUbicacion> responseBuscada = requestBuscada.execute();

    return this.generarLocalizacionUbi(responseBuscada.body());
  }

  public List<Localizacion> provincias() throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeProvincias> requestBuscada = geoRefService.provincias("id, nombre");
    Response<ListadoDeProvincias> responseBuscada = requestBuscada.execute();
    return this.generarLocalizacionProv(responseBuscada.body());
  }
  public List<Localizacion> municipiosDeProvincia(Localizacion provincia) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeMunicipios> requestBuscada = geoRefService.municipios(provincia.getId(), "id, nombre");
    Response<ListadoDeMunicipios> responseBuscada = requestBuscada.execute();
    return this.generarLocalizacionMuni(responseBuscada.body());
  }
  public List<Localizacion> departamentosDeProvincia(Localizacion provincia) throws IOException {
    GeoRefService geoRefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeDepartamentos> requestBuscada = geoRefService.departamentos(provincia.getId(), "id, nombre");
    Response<ListadoDeDepartamentos> responseBuscada = requestBuscada.execute();
    return this.generarLocalizacionDep(responseBuscada.body());
  }

  private List<Localizacion> generarLocalizacionDep(ListadoDeDepartamentos lista){
    return lista.departamentos.stream().map(dep -> new Localizacion(dep.id, dep.nombre, DEPARTAMENTO)).toList();
  }
  private List<Localizacion> generarLocalizacionProv(ListadoDeProvincias lista){
    return lista.provincias.stream().map(prov -> new Localizacion(prov.id, prov.nombre, PROVINCIA)).toList();
  }
  private List<Localizacion> generarLocalizacionMuni(ListadoDeMunicipios lista){
    return lista.municipios.stream().map(muni -> new Localizacion(muni.id, muni.nombre, MUNICIPIO)).toList();
  }
  private List<Localizacion> generarLocalizacionUbi(ResponseUbicacion ubicacion){
    List<Localizacion> localizaciones = new ArrayList<>();

    localizaciones.add(new Localizacion(ubicacion.ubicacionGeoRef.provincia.id, ubicacion.ubicacionGeoRef.provincia.nombre, PROVINCIA));
    localizaciones.add(new Localizacion(ubicacion.ubicacionGeoRef.municipio.id, ubicacion.ubicacionGeoRef.municipio.nombre, MUNICIPIO));
    localizaciones.add(new Localizacion(ubicacion.ubicacionGeoRef.departamento.id, ubicacion.ubicacionGeoRef.departamento.nombre, DEPARTAMENTO));

    return localizaciones;
  }
}
