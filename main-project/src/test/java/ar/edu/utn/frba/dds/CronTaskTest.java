package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskPorMinuto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CronTaskTest {
  private static CronTaskPorMinuto cronTaskPorMinuto;
  private static class MiObjeto {
    public Integer numero;
  }
  @BeforeEach
  public void init(){
    cronTaskPorMinuto = new CronTaskPorMinuto();
  }

  @Test
  @DisplayName("Un cambio en un objeto afectado por un CronTask de 5 segundos debe verse reflejado")
  public void cambioEnObjetoAfectadoPorCrontaskEsReflejado() throws InterruptedException {
    MiObjeto miObj = new MiObjeto();
    miObj.numero = 0;
    cronTaskPorMinuto.setCantMinutos(1L);
    cronTaskPorMinuto.iniciar(() ->{miObj.numero = 1;});
    assertEquals(miObj.numero,0);
    Thread.sleep(61000);
    assertEquals(miObj.numero,1);
  }
}
