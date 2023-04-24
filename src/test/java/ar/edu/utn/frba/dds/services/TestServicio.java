package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.services.Estacion;
import ar.edu.utn.frba.dds.domain.services.Linea;
import ar.edu.utn.frba.dds.domain.services.Servicio;
import ar.edu.utn.frba.dds.domain.services.SubtipoServicio;
import ar.edu.utn.frba.dds.domain.services.TipoServicio;
import ar.edu.utn.frba.dds.domain.services.TipoTransporte;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestServicio {
  private Servicio servicio;

  @BeforeEach
  public void Init() {
    List<SubtipoServicio> subtiposBanioHombreConCambiador = new ArrayList<SubtipoServicio>();

    SubtipoServicio subtipoHombre = new SubtipoServicio(0, "Hombres");
    subtiposBanioHombreConCambiador.add(subtipoHombre);
    SubtipoServicio subtipoCambiadorBebe = new SubtipoServicio(1, "Con cambiador de bebé");
    subtiposBanioHombreConCambiador.add(subtipoCambiadorBebe);

    this.servicio = new Servicio(0, new TipoServicio(0, "Baño"), subtiposBanioHombreConCambiador);
  }

  @Test
  @DisplayName("Un servicio puede agregar un nuevo subtipo")
  public void agregoSubtipoCorrectamente() {

    //arrange
    SubtipoServicio subtipoDuchas = new SubtipoServicio(2, "Con duchas");

    //act
    this.servicio.agregarSubtipo(subtipoDuchas);

    //assert
    Assertions.assertEquals(3, this.servicio.getSubtipos().size());
  }

  @Test
  @DisplayName("Un servicio puede eliminar un subtipo")
  public void eliminoSubtipoCorrectamente() {

    //arrange

    //act
    this.servicio.eliminarSubtipo(new SubtipoServicio(0, "Hombres"));

    //assert
    Assertions.assertEquals(1, this.servicio.getSubtipos().size());
  }
}
