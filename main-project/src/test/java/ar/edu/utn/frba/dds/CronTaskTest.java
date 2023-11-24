package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.controllers.utils.CronTask;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CronTaskTest {
  private static CronTask cronTask;
  private static class MiObjeto {
    public Integer numero;
  }
  @BeforeEach
  public void init(){
    cronTask = new CronTask();
  }

  @Test
  @DisplayName("Un cambio en un objeto afectado por un CronTask de 5 segundos debe verse reflejado")
  public void cambioEnObjetoAfectadoPorCrontaskEsReflejado() throws InterruptedException {
    MiObjeto miObj = new MiObjeto();
    miObj.numero = 0;
    cronTask.inicializar(() ->{miObj.numero = 1;},LocalDateTime.now(),5);
    Thread.sleep(4500);
    assertEquals(miObj.numero,0);
    Thread.sleep(500);
    assertEquals(miObj.numero,1);
  }
}
