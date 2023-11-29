package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EstablecimientoRepositorioTests  implements WithSimplePersistenceUnit {
  private EstablecimientoRepositorio repositorioEstablecimiento;
  private ServicioRepositorio repositorioServicio;

  @BeforeEach
  public void init() throws IOException {
    this.repositorioEstablecimiento = new EstablecimientoRepositorio();
    this.repositorioServicio = new ServicioRepositorio();
  }

  @Test
  @DisplayName("Se puede guardar un establecimiento correctamente")
  public void ejecutaGuardarCorrectamente_ConEstablecimientoSimple(){
    //arrange
    Establecimiento establecimientoNuevo = new Establecimiento();
    establecimientoNuevo.setNombre("Test Establecimiento");

    //act
    this.repositorioEstablecimiento.guardar(establecimientoNuevo);

    Establecimiento establecimientoRecuperado = this.repositorioEstablecimiento.buscarPorId(establecimientoNuevo.getId());

    //assert
    Assertions.assertTrue(establecimientoNuevo.getId() > 0, "Establecimiento: genera id correctamente");
    Assertions.assertNotNull(establecimientoRecuperado, "Establecimiento: se recupera instancia de la db");
    Assertions.assertEquals("Test Establecimiento", establecimientoRecuperado.getNombre(), "Establecimiento: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede guardar un establecimiento correctamente con sus servicios prestados")
  public void ejecutaGuardarCorrectamente_ConEstablecimientoConServicioPrestado(){
    //arrange
    Establecimiento establecimientoNuevo = new Establecimiento();
    establecimientoNuevo.setNombre("Test Establacimiento");

    ServicioPrestado servicioPrestadoNuevo = new ServicioPrestado();
    Servicio servicioNuevo = new Servicio();
    servicioNuevo.setNombre("Test Servicio");
    this.repositorioServicio.guardar(servicioNuevo);

    servicioPrestadoNuevo.setServicio(servicioNuevo);
    establecimientoNuevo.getServiciosPrestados().add(servicioPrestadoNuevo);

    //act
    this.repositorioEstablecimiento.guardar(establecimientoNuevo);

    Establecimiento establecimientoRecuperado = this.repositorioEstablecimiento.buscarPorId(establecimientoNuevo.getId());
    ServicioPrestado servicioPrestadoRecuperado = establecimientoRecuperado.getServiciosPrestados().stream().findFirst().get();
    Servicio servicioRecuperado = servicioPrestadoRecuperado.getServicio();

    //assert
    Assertions.assertTrue(establecimientoNuevo.getId() > 0, "Establecimiento: genera id correctamente");
    Assertions.assertNotNull(establecimientoRecuperado, "Establecimiento: se recupera instancia de la db");
    Assertions.assertEquals("Test Establacimiento", establecimientoRecuperado.getNombre(), "Establecimiento: nombre guardado correctamente");

    Assertions.assertTrue(servicioPrestadoNuevo.getId() > 0, "ServicioPrestado: genera id correctamente");
    Assertions.assertNotNull(servicioPrestadoRecuperado, "ServicioPrestado: se recupera instancia de la db");

    Assertions.assertTrue(servicioRecuperado.getId() > 0, "Servicio: genera id correctamente");
    Assertions.assertNotNull(servicioRecuperado, "Servicio: se recupera instancia de la db");
    Assertions.assertEquals("Test Servicio", servicioRecuperado.getNombre(), "Servicio: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede actualizar un establecimiento correctamente")
  public void ejecutaActualizarCorrectamente_ConEstablecimientoSimple(){
    //arrange
    Establecimiento establecimientoNuevo = new Establecimiento();
    establecimientoNuevo.setNombre("Test Establecimiento");

    //act
    this.repositorioEstablecimiento.guardar(establecimientoNuevo);
    Establecimiento establecimientoAModificar = this.repositorioEstablecimiento.buscarPorId(establecimientoNuevo.getId());
    establecimientoAModificar.setNombre("Test Establecimiento Actualizado");
    this.repositorioEstablecimiento.actualizar(establecimientoAModificar);
    Establecimiento establecimientoRecuperado = this.repositorioEstablecimiento.buscarPorId(establecimientoAModificar.getId());

    //assert
    Assertions.assertEquals("Test Establecimiento Actualizado", establecimientoRecuperado.getNombre(), "Establecimiento: nombre guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar establecimiento correctamente")
  public void ejecutaEliminarCorrectamente_ConEstablecimientoSimple(){
    //arrange
    Establecimiento establecimientoNuevo = new Establecimiento();
    establecimientoNuevo.setNombre("Test Establecimiento");

    //act
    this.repositorioEstablecimiento.guardar(establecimientoNuevo);
    this.repositorioEstablecimiento.eliminar(establecimientoNuevo);

    Establecimiento establecimientoRecuperado = this.repositorioEstablecimiento.buscarPorId(establecimientoNuevo.getId());

    //assert
    Assertions.assertNull(establecimientoRecuperado, "Establecimiento: el establecimiento es eliminado");
  }
}
