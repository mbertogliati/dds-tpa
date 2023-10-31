package ar.edu.utn.frba.dds.modelos.validacion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class ObtenerTopPeoresPasswords implements ObtenerListaString {
  @Getter
  @Setter
  private String archivo = "main-project/src/main/resources/public/validacion/10000WorstPasswords.txt";
  private List<String> lista = new ArrayList<>();
  private static ObtenerTopPeoresPasswords instancia = null;

  public static ObtenerTopPeoresPasswords instancia(){
    if(instancia == null){
      instancia = new ObtenerTopPeoresPasswords();
    }
    return instancia;
  }

  private ObtenerTopPeoresPasswords(){

    String envWorstPasswords = System.getenv("WORST_PASSWORDS_FILE");
    if(envWorstPasswords != null && !envWorstPasswords.isEmpty())
      this.archivo = envWorstPasswords;

    this.inicializarLista();
  }

  private void inicializarLista() {
    try {
      Path path = Paths.get(this.archivo);
      this.lista = Files.readAllLines(path);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public List<String> obtenerLista(){
    return lista;
  }
}
