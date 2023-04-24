package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.services.Servicio;
import ar.edu.utn.frba.dds.domain.services.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.services.SubtipoServicio;
import ar.edu.utn.frba.dds.domain.services.TipoServicio;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestServicioPrestado {
  private TipoServicio tipoBanio = new TipoServicio(0, "Baño");
  private TipoServicio tipoMedioElevacion = new TipoServicio(1, "Medio de Elevación");

  private SubtipoServicio subtipoHombre = new SubtipoServicio(0, "Hombres");
  private SubtipoServicio subtipoMujer = new SubtipoServicio(1, "Mujeres");
  private SubtipoServicio subtipoSinGenero = new SubtipoServicio(2, "Sin género");
  private SubtipoServicio subtipoEscaleraConvencional = new SubtipoServicio(3, "Escalera convencional");
  private SubtipoServicio subtipoEscaleraMecanica = new SubtipoServicio(4, "Escalera mecánica");
  private SubtipoServicio subtipoAscensor = new SubtipoServicio(5, "Ascensor");
  private SubtipoServicio subtipoCambiadorBebe = new SubtipoServicio(6, "Con cambiador de bebé");
  private SubtipoServicio subtipoInicial = new SubtipoServicio(7, "Inicial");
  private SubtipoServicio subtipoFinal = new SubtipoServicio(8, "Final");

  private Servicio banioHombres;
  private Servicio banioMujeres;
  private Servicio banioSinGenero;
  private Servicio escaleraMecanicaFinal;
  private Servicio ascensorInicial;

  private List<ServicioPrestado> serviciosPrestados = new ArrayList<ServicioPrestado>();

  @BeforeEach
  public void Init() {
    List<SubtipoServicio> subtiposBanioHombreConCambiador = new ArrayList<SubtipoServicio>();
    subtiposBanioHombreConCambiador.add(subtipoHombre);
    subtiposBanioHombreConCambiador.add(subtipoCambiadorBebe);
    this.banioHombres = new Servicio(0, tipoBanio, subtiposBanioHombreConCambiador);

    List<SubtipoServicio> subtiposBanioSinGenero = new ArrayList<SubtipoServicio>();
    subtiposBanioSinGenero.add(subtipoSinGenero);
    this.banioSinGenero = new Servicio(1, tipoBanio, subtiposBanioSinGenero);

    List<SubtipoServicio> subtiposEscaleraMecanicaFinal = new ArrayList<SubtipoServicio>();
    subtiposEscaleraMecanicaFinal.add(subtipoEscaleraMecanica);
    subtiposEscaleraMecanicaFinal.add(subtipoFinal);
    this.escaleraMecanicaFinal = new Servicio(2, tipoMedioElevacion, subtiposEscaleraMecanicaFinal);

    List<SubtipoServicio> subtiposAscensorInicial = new ArrayList<SubtipoServicio>();
    subtiposAscensorInicial.add(subtipoAscensor);
    subtiposAscensorInicial.add(subtipoInicial);
    this.ascensorInicial = new Servicio(3, tipoMedioElevacion, subtiposAscensorInicial);

    serviciosPrestados.add(new ServicioPrestado(0, ascensorInicial, true));
    serviciosPrestados.add(new ServicioPrestado(1, escaleraMecanicaFinal, false));
    serviciosPrestados.add(new ServicioPrestado(2, banioHombres, true));
    serviciosPrestados.add(new ServicioPrestado(3, banioSinGenero, true));
  }

  @Test
  @DisplayName("Se puede establecer un servicio prestado como no accesible")
  public void establezcoComoNoAccesible() {

    //arrange

    //act
    serviciosPrestados.get(0).noEsAccesible();

    //assert
    Assertions.assertEquals(false, serviciosPrestados.get(0).getAccesibilidad());
  }

  @Test
  @DisplayName("Se puede establecer un servicio prestado como accesible")
  public void establezcoComoAccesible() {

    //arrange

    //act
    serviciosPrestados.get(0).esAccesible();

    //assert
    Assertions.assertEquals(true, serviciosPrestados.get(0).getAccesibilidad());
  }
}
