package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.meta_datos_geo.Municipio;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonaTests {
  @Test
  @DisplayName("Se puede asignar una ubicacion actual a una persona")
  public void setUbicacionActualAPersona(){
    //arrange
    Persona persona = new Persona("Nombre", "Apellido");
    Ubicacion ubicacion = new Ubicacion(1.5f, 1.5f);

    //act
    persona.setUbicacionActual(ubicacion);

    //assert
    Assertions.assertEquals(1.5f, persona.getUbicacionActual().getCoordenada().getLatitud());
    Assertions.assertEquals(1.5f, persona.getUbicacionActual().getCoordenada().getLongitud());
  }

  @Test
  @DisplayName("Se puede consultar si un servicio prestado es de interés para una persona")
  public void consultaInteresPersona() {
    Provincia provincia = new Provincia("1", "Buenos Aires");
    Departamento departamento = new Departamento("4", "Departamento de San Agustín");
    Municipio municipio1 = new Municipio("1", "Lomas de Zamora");
    Municipio municipio2 = new Municipio("2", "Lanús");
    Localidad localidad1 = new Localidad("1", "Temperley");

    Persona persona = new Persona("Nombre", "Apellido");
    Ubicacion ubicacion1 = new Ubicacion(provincia, municipio1, departamento, localidad1);
    Ubicacion ubicacion2 = new Ubicacion(provincia, municipio2);

    Entidad entidad = new Entidad("entidad 1", "Banco");
    Servicio servicio1 = new Servicio();
    servicio1.setId(1);
    Servicio servicio2 = new Servicio();
    servicio1.setId(2);
    Establecimiento establecimiento = new Establecimiento("estab 1", "sucursal");
    establecimiento.setUbicacion(ubicacion1);
    establecimiento.setEntidad(entidad);
    ServicioPrestado servicioPrestado = establecimiento.agregarServicio(servicio1);
    entidad.agregarEstablecimiento(establecimiento);

    Establecimiento establecimiento2 = new Establecimiento("estab 2", "sucursal");
    establecimiento2.setUbicacion(ubicacion2);
    ServicioPrestado servicioPrestado2 = establecimiento2.agregarServicio(servicio2);

    persona.agregarEntidadInteres(entidad);
    persona.agregarServicioInteres(servicio1);
    persona.agregarServicioInteres(servicio2);

    Assertions.assertTrue(persona.servicioPrestadoEsDeInteres(servicioPrestado));
    Assertions.assertFalse(persona.servicioPrestadoEsDeInteres(servicioPrestado2));
  }


}
