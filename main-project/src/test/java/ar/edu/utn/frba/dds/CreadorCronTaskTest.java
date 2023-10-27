package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.controllers.utils.CreadorCronTask;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreadorCronTaskTest {
  private static CreadorCronTask creadorCronTask ;
  private static class MiObjeto {
    public Integer numero;
  }
  @BeforeEach
  public void init(){
    creadorCronTask = new CreadorCronTask();
  }

  @Test
  @DisplayName("Un cambio en un objeto afectado por un CronTask de 5 segundos debe verse reflejado")
  public void cambioEnObjetoAfectadoPorCrontaskEsReflejado() throws InterruptedException {
    MiObjeto miObj = new MiObjeto();
    miObj.numero = 0;
    creadorCronTask.crearCronTask(() ->{miObj.numero = 1;},LocalDateTime.now(),5);
    Thread.sleep(4500);
    assertEquals(miObj.numero,0);
    Thread.sleep(500);
    assertEquals(miObj.numero,1);
  }
}
