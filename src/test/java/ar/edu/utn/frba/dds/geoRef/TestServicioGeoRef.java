package ar.edu.utn.frba.dds.geoRef;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestServicioGeoRef{

  @BeforeEach
  public void init(){
  }

  @Test
  @DisplayName("Se obtiene la provincia correcta al buscar por latitud y longitud")
  public void seObtieneLaProvinciaCorrecta() throws IOException {

    ServicioGeoRef servicioGeoRef = ServicioGeoRef.instancia();
    Float latitud = (float) -37.54787204004173;
    Float longitud = (float) -61.46888869402542;
    String provincia = servicioGeoRef.provincia(latitud, longitud).getNombre(); //Buenos Aires
    assertEquals(provincia, "Buenos Aires");

    latitud = (float) -38.37937303006633;
    longitud = (float) -63.51234577394965;
    provincia = servicioGeoRef.provincia(latitud,longitud).getNombre(); //La Pampa
    assert(provincia.equals("La Pampa"));
    assertEquals(provincia, "La Pampa");

    latitud = (float)  -24.38287297032664;
    longitud = (float) -65.54097791735738;
    provincia = servicioGeoRef.provincia(latitud,longitud).getNombre(); //Jujuy
    assertEquals(provincia, "Jujuy");

    latitud = (float)  -75.70025211682918;
    longitud = (float) -72.27246873936852;
    provincia = servicioGeoRef.provincia(latitud,longitud).getNombre(); //Antartida
    assertEquals(provincia, "Tierra del Fuego, Antártida e Islas del Atlántico Sur");

  }
  @Test
  @DisplayName("Se obtiene el departamento correcto al buscar por latitud y longitud")
  public void seObtieneElDepartamentoCorrecto() throws IOException {

    ServicioGeoRef servicioGeoRef = ServicioGeoRef.instancia();
    Float latitud = (float) -37.54787204004173;
    Float longitud = (float) -61.46888869402542;
    String departamento = servicioGeoRef.departamento(latitud, longitud).getNombre(); //General La Madrid
    assertEquals(departamento, "General La Madrid");

    latitud = (float) -38.37937303006633;
    longitud = (float) -63.51234577394965;
    departamento = servicioGeoRef.departamento(latitud,longitud).getNombre(); //Caleu Caleu
    assertEquals(departamento, "Caleu Caleu");

    latitud = (float)  -24.38287297032664;
    longitud = (float) -65.54097791735738;
    departamento = servicioGeoRef.departamento(latitud,longitud).getNombre(); //San Antonio
    assertEquals(departamento, "San Antonio");

    latitud = (float)  -75.70025211682918;
    longitud = (float) -72.27246873936852;
    departamento = servicioGeoRef.departamento(latitud,longitud).getNombre(); //Antártida Argentina
    assertEquals(departamento, "Antártida Argentina");

  }
  @Test
  @DisplayName("Se obtiene el municipio correcto al buscar por latitud y longitud")
  public void seObtieneElMunicipioCorrecto() throws IOException {

    ServicioGeoRef servicioGeoRef = ServicioGeoRef.instancia();
    Float latitud = (float) -37.54787204004173;
    Float longitud = (float) -61.46888869402542;
    String municipio = servicioGeoRef.municipio(latitud, longitud).getNombre(); //General La Madrid
    assertEquals(municipio, "General la Madrid");

    latitud = (float) -38.37937303006633;
    longitud = (float) -63.51234577394965;
    municipio = servicioGeoRef.municipio(latitud,longitud).getNombre(); //Caleu Caleu
    assertEquals(municipio, "Jacinto Arauz");

    latitud = (float)  -24.38287297032664;
    longitud = (float) -65.54097791735738;
    municipio = servicioGeoRef.municipio(latitud,longitud).getNombre(); //San Antonio
    assertEquals(municipio, "San Antonio");

    latitud = (float)  -75.70025211682918;
    longitud = (float) -72.27246873936852;
    municipio = servicioGeoRef.municipio(latitud,longitud).getNombre(); //Antártida Argentina
    assertEquals(municipio, null);

  }
}
