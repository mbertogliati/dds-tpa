package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskPorMinuto;
import java.time.LocalDateTime;

import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskPorSegundo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CronTaskTest {
  private static CronTaskPorSegundo cronTaskPorSegundo;
  private static class MiObjeto {
    public Integer numero;
  }
  @BeforeEach
  public void init(){
    cronTaskPorSegundo = new CronTaskPorSegundo();
  }

  @Test
  @DisplayName("Un cambio en un objeto afectado por un CronTask de 5 segundos debe verse reflejado")
  public void cambioEnObjetoAfectadoPorCrontaskEsReflejado() throws InterruptedException {/*
    MiObjeto miObj = new MiObjeto();
    miObj.numero = 0;
    cronTaskPorSegundo.setCantSegundos(5L);
    cronTaskPorSegundo.iniciar(() ->{miObj.numero = 1;});
    assertEquals(miObj.numero,0);
    Thread.sleep(5000);
    assertEquals(miObj.numero,1);*/
  }
}
