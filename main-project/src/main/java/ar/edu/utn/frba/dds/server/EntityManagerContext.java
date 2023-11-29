package ar.edu.utn.frba.dds.server;

import javax.persistence.EntityManager;

public class EntityManagerContext {
  private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

  public static EntityManager getEntityManager() {
    return entityManagerThreadLocal.get();
  }

  public static void setEntityManager(EntityManager entityManager) {
    entityManagerThreadLocal.set(entityManager);
  }

  public static void closeEntityManager() {
    EntityManager entityManager = entityManagerThreadLocal.get();
    if (entityManager != null && entityManager.isOpen() && !entityManager.getTransaction().isActive()) {
      entityManager.close();
    }else{
      System.out.println("No se cerró el EM, hay transacciones activas");
    }
    entityManagerThreadLocal.remove();
  }
}
