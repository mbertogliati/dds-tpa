package ar.edu.utn.frba.dds.controllers.utils;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

public class CreadorEntityManager implements WithSimplePersistenceUnit {
  public CreadorEntityManager(){}
  public EntityManager entityManagerCreado(){
    return entityManager();
  }
}
