package ar.edu.utn.frba.dds.geoRef;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoRefService {
  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("campos") String campos);

  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("id") String idProvincia, @Query("campos") String campos);

  @GET("departamentos")
  Call<ListadoDeDepartamentos> departamentos(@Query("id") String idProvincia, @Query("campos") String campos);

  @GET("ubicacion")
  Call<ResponseUbicacion> ubicacion(@Query("lat") float latitud, @Query("lon") float longitud);

}
