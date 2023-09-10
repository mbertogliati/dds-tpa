package ar.edu.utn.frba.dds.domain.validacion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ObtenerTopPeoresPasswords implements ObtenerListaString {
  private final String archivo = "src/main/java/ar/edu/utn/frba/dds/domain/validacion/10000WorstPasswords.txt";
  private List<String> lista = new ArrayList<>();
  private static ObtenerTopPeoresPasswords instancia = null;

  public static ObtenerTopPeoresPasswords instancia(){
    if(instancia == null){
      instancia = new ObtenerTopPeoresPasswords();
    }
    return instancia;
  }

  private ObtenerTopPeoresPasswords(){
    Path path = Paths.get(archivo);

    try {
      lista = Files.readAllLines(path);
    }catch (IOException e){
      e.printStackTrace();
    }

  }

  @Override
  public List<String> obtenerLista(){
    return lista;
  }
}
