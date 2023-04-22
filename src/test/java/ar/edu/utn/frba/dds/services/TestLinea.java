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


  @BeforeEach
  public void Init() {
    this.tipoTransporteTren = new TipoTransporte(0, "Tren");
    this.tipoTransporteSubte = new TipoTransporte(1, "Subte");
    this.ubicacion = new float[] { 0.0f, 0.0f };

    Estacion estacionRetiro = new Estacion("Retiro", this.ubicacion , null);
    Estacion estacionJLSuarez = new Estacion("J.L. Suarez", this.ubicacion, null);
    Estacion estacionVPueyrredon = new Estacion("V. Pueyrredon", this.ubicacion, null);

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
    Estacion estacionVUrquiza = new Estacion("V. Urquiza", this.ubicacion, null);

    //act
    this.linea.agregarEstacion(estacionVUrquiza);

    //assert
    Assertions.assertEquals(4, this.linea.getEstaciones().size());
  }

}
