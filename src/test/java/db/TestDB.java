package db;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import org.junit.jupiter.api.Test;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;

public class TestDB implements WithSimplePersistenceUnit {

  @Test
  public void crearEntidad(){
    Entidad entidad = new Entidad();
    entidad.setNombre("nombre entidad");

    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(entidad); //INSERT INTO ....
    tx.commit();
  }

}
