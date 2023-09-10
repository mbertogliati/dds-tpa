package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class MainExample implements WithSimplePersistenceUnit{
  //TODO: REVISAR CASCADA Y ATRIBUTOS DE LA PERSISTENCIA
  //TODO: HACER DER
  public static void main(String[] args) {
    new MainExample().iniciar();
  }

  private void iniciar() {
    Entidad entidad = new Entidad();
    entidad.setNombre("entidad prueba23");

    Establecimiento establecimiento = new Establecimiento();
    establecimiento.setNombre("el establecimientoooo");

   // entidad.agregarEstablecimiento(establecimiento);

    EstablecimientoRepositorio repoEst = new EstablecimientoRepositorio(entityManager());
    repoEst.guardar(establecimiento);

    EntidadRepositorio repo = new EntidadRepositorio(entityManager());
    repo.guardar(entidad);



  }
}
