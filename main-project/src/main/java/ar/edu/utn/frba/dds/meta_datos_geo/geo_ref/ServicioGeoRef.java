package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref;

import ar.edu.utn.frba.dds.domain.utilidades.Coordenada;
import ar.edu.utn.frba.dds.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ListadoDeDepartamentos;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ListadoDeLocalidades;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ListadoDeProvincias;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ResponseUbicacion;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoRef implements AdapterProveedorMetadatosGeograficos {
  private static ServicioGeoRef instancia;
  private static final String urlApi = "https://apis.datos.gob.ar/georef/api/";
  private final GeoRefService geoRefService;

  //Inicio constructores
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
  //Fin constructores

  public MetadatoGeografico obtenerMetadatoGeografico(Coordenada coordenada) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(coordenada);

    Provincia provincia = null;
    Localidad localidad = null;
    Departamento departamento = null;

    if (response.ubicacion.provincia != null) {
      provincia = new Provincia(response.ubicacion.provincia.id, response.ubicacion.provincia.nombre);
    }

    if (response.ubicacion.localidad != null) {
      localidad = new Localidad(response.ubicacion.localidad.id, response.ubicacion.localidad.nombre);
    }

    if (response.ubicacion.departamento != null) {
      departamento = new Departamento(response.ubicacion.departamento.id, response.ubicacion.departamento.nombre);
    }

    return new MetadatoGeografico(provincia, departamento, localidad);
  }

  public Provincia obtenerProvincia(Coordenada coordenada) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(coordenada);
    return new Provincia(response.ubicacion.provincia.id, response.ubicacion.provincia.nombre);
  }
  public Departamento obtenerDepartamento(Coordenada coordenada) throws  IOException {
    ResponseUbicacion response = getResponseUbicacion(coordenada);
    return new Departamento(response.ubicacion.departamento.id, response.ubicacion.departamento.nombre);
  }
  public Localidad obtenerLocalidad(Coordenada coordenada) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(coordenada);
    return new Localidad(response.ubicacion.localidad.id, response.ubicacion.localidad.nombre);
  }

  private ResponseUbicacion getResponseUbicacion(Coordenada coordenada) throws IOException {
    Call<ResponseUbicacion> requestBuscada = this.geoRefService.ubicacion(coordenada.getLatitud(), coordenada.getLongitud());
    Response<ResponseUbicacion> responseBuscada = requestBuscada.execute();
    return responseBuscada.body();
  }

  public List<Provincia> provincias() throws IOException {
    Call<ListadoDeProvincias> requestBuscada = this.geoRefService.provincias("id, nombre");
    Response<ListadoDeProvincias> responseBuscada = requestBuscada.execute();
    return responseBuscada.body().provincias.stream().map(prov -> new Provincia(prov.id, prov.nombre)).toList();
  }
  public List<Departamento> departamentosDeProvincia(Provincia provincia) throws IOException {
    Call<ListadoDeDepartamentos> requestBuscada = this.geoRefService.departamentos(String.valueOf(provincia.getId()), "id, nombre");
    Response<ListadoDeDepartamentos> responseBuscada = requestBuscada.execute();
    ListadoDeDepartamentos lista = responseBuscada.body();
    return lista.departamentos.stream().map(dep -> new Departamento(dep.id, dep.nombre)).toList();
  }
  public List<Localidad> localidadesDeDepartamento(Departamento departamento, Provincia provincia) throws IOException {
    Call<ListadoDeLocalidades> requestBuscada = this.geoRefService.localidades(departamento.getNombre(), provincia.getNombre(), "id, nombre");
    Response<ListadoDeLocalidades> responseBuscada = requestBuscada.execute();
    ListadoDeLocalidades lista = responseBuscada.body();
    return lista.localidades.stream().map(dep -> new Localidad(dep.id, dep.nombre)).toList();
  }
}
