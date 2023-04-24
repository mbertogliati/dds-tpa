package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.services.Linea;
import ar.edu.utn.frba.dds.domain.services.Estacion;
import ar.edu.utn.frba.dds.domain.services.TipoTransporte;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestLinea {
  private float[] ubicacion;
  private TipoTransporte tipoTransporteTren;
  private TipoTransporte tipoTransporteSubte;

  private Linea linea;

  private Estacion estacionRetiro = new Estacion(0, "Retiro", this.ubicacion , null);
  private Estacion estacionJLSuarez = new Estacion(1, "J.L. Suarez", this.ubicacion, null);
  private Estacion estacionVPueyrredon = new Estacion(2, "V. Pueyrredon", this.ubicacion, null);


  @BeforeEach
  public void Init() {
    this.tipoTransporteTren = new TipoTransporte(0, "Tren");
    this.tipoTransporteSubte = new TipoTransporte(1, "Subte");
    this.ubicacion = new float[] { 0.0f, 0.0f };

    List<Estacion> estaciones = new ArrayList<Estacion>();

    estaciones.add(estacionRetiro);
    estaciones.add(estacionVPueyrredon);
    estaciones.add(estacionJLSuarez);

    this.linea = new Linea(this.tipoTransporteTren, "Mitre",  estacionRetiro, estacionJLSuarez, estaciones);
  }

  @Test
  @DisplayName("Linea puede agregar una estacion más")
  public void agregoEstacionCorrectamente() {

    //arrange
    Estacion estacionVUrquiza = new Estacion(3, "V. Urquiza", this.ubicacion, null);

    //act
    this.linea.agregarEstacion(estacionVUrquiza);

    //assert
    Assertions.assertEquals(4, this.linea.getEstaciones().size());
  }

  @Test
  @DisplayName("Linea puede eliminar una estacion")
  public void eliminoEstacionCorrectamente() {

    //arrange

    //act
    this.linea.eliminarEstacion(estacionVPueyrredon);

    //assert
    Assertions.assertEquals(2, this.linea.getEstaciones().size());
  }

}
