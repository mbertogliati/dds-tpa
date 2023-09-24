package db;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

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
