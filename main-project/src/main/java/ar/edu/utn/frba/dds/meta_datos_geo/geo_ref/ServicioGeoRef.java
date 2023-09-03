package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref;

import ar.edu.utn.frba.dds.domain.utilidades.Coordenada;
import ar.edu.utn.frba.dds.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.meta_datos_geo.Municipio;
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

  public MetadatoGeografico obtenerMetadatoGeografico(Coordenada coordenada) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(coordenada);

    Provincia provincia = null;
    Municipio municipio = null;
    Localidad localidad = null;
    Departamento departamento = null;

    if (response.ubicacion.provincia != null) {
      provincia = new Provincia(response.ubicacion.provincia.id, response.ubicacion.provincia.nombre);
    }

    if (response.ubicacion.municipio != null) {
      municipio = new Municipio(response.ubicacion.municipio.id, response.ubicacion.municipio.nombre);
    }

    if (response.ubicacion.localidad != null) {
      localidad = new Localidad(response.ubicacion.localidad.id, response.ubicacion.localidad.nombre);
    }

    if (response.ubicacion.departamento != null) {
      departamento = new Departamento(response.ubicacion.departamento.id, response.ubicacion.departamento.nombre);
    }

    return new MetadatoGeografico(provincia, municipio, departamento, localidad);
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
  public Municipio obtenerMunicipio(Coordenada coordenada) throws IOException {
    ResponseUbicacion response = getResponseUbicacion(coordenada);
    return new Municipio(response.ubicacion.municipio.id, response.ubicacion.municipio.nombre);
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
  public List<Municipio> municipiosDeProvincia(Provincia provincia) throws IOException {
    Call<ListadoDeMunicipios> requestBuscada = this.geoRefService.municipios(String.valueOf(provincia.getId()), "id, nombre");
    Response<ListadoDeMunicipios> responseBuscada = requestBuscada.execute();
    ListadoDeMunicipios lista = responseBuscada.body();
    return lista.municipios.stream().map(mun -> new Municipio(mun.id, mun.nombre)).toList();
  }
  public List<Departamento> departamentosDeProvincia(Provincia provincia) throws IOException {
    Call<ListadoDeDepartamentos> requestBuscada = this.geoRefService.departamentos(String.valueOf(provincia.getId()), "id, nombre");
    Response<ListadoDeDepartamentos> responseBuscada = requestBuscada.execute();
    ListadoDeDepartamentos lista = responseBuscada.body();
    return lista.departamentos.stream().map(dep -> new Departamento(dep.id, dep.nombre)).toList();
  }

  public List<Localidad> localidadesDeProvincia(Provincia provincia) throws IOException {
    Call<ListadoDeLocalidades> call = this.geoRefService.localidades(String.valueOf(provincia.getId()), "id, nombre");
    Response<ListadoDeLocalidades> response = call.execute();
    ListadoDeLocalidades lista = response.body();
    return lista.localidades.stream().map(loc -> new Localidad(loc.id, loc.nombre)).toList();
  }
}