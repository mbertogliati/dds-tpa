package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;

public class MainExample implements WithSimplePersistenceUnit{
  public static void main(String[] args) {
    new MainExample().iniciar();
  }

  private void iniciar() {
    Entidad entidad = new Entidad();
    entidad.setNombre("nombre entidad");

    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(entidad); //INSERT INTO ....
    tx.commit();
  }
}
