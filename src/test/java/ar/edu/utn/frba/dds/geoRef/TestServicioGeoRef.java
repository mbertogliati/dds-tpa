package ar.edu.utn.frba.dds.geoRef;

import ar.edu.utn.frba.dds.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.domain.comunidades.Membresia;
import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestServicioGeoRef{

  @BeforeEach
  public void init(){
  }

  @Test
  @DisplayName("Si se busca por latitud y longitud, se obtiene la provincia correcta")
  public void seObtieneLaProvinciaCorrecta(){

    double latitud =-37.54787204004173;
    double longitud = -61.46888869402542;

    ServicioGeoRef servicioGeoRef = ServicioGeoRef.instancia();
  }
}
