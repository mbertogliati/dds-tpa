package ar.edu.utn.frba.dds.validacion;

import ar.edu.utn.frba.dds.geoRef.ServicioGeoRef;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ObtenerTopPeoresPasswordsURL implements ObtenerListaString {
  private final String path = "10000WorstPasswords.txt";
  private List<String> lista;
  private static ObtenerTopPeoresPasswordsURL instancia = null;

  public static ObtenerTopPeoresPasswordsURL instancia(){
    if(instancia == null){
      instancia = new ObtenerTopPeoresPasswordsURL();
    }
    return instancia;
  }

  private ObtenerTopPeoresPasswordsURL(){
    try {
      this.lista = Files.readAllLines(Paths.get(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> obtenerLista(){
    return lista;
  }
}
