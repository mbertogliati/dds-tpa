package ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models.ListadoDeDepartamentos;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models.ListadoDeLocalidades;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models.ListadoDeProvincias;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models.ResponseUbicacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoRefService {
  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("campos") String campos);

  @GET("departamentos")
  Call<ListadoDeDepartamentos> departamentos(@Query("provincia") String idProvincia, @Query("campos") String campos, @Query("max") String max);

  @GET("localidades")
  Call<ListadoDeLocalidades> localidades(@Query("departamento") String departamento, @Query("provincia") String provincia, @Query("campos") String campos, @Query("max") String max);

  @GET("ubicacion")
  Call<ResponseUbicacion> ubicacion(@Query("lat") float latitud, @Query("lon") float longitud);
}
