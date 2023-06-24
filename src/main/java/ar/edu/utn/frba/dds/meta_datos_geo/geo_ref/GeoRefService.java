package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref;

import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ListadoDeLocalidades;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ListadoDeProvincias;
import ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models.ResponseUbicacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoRefService {
  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("campos") String campos);

  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("id") String idProvincia, @Query("campos") String campos);

  @GET("departamentos")
  Call<ListadoDeLocalidades> departamentos(@Query("id") String idProvincia, @Query("campos") String campos);

  @GET("ubicacion")
  Call<ResponseUbicacion> ubicacion(@Query("lat") float latitud, @Query("lon") float longitud);
}
