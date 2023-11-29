package ar.edu.utn.frba.dds.repositorios;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransactionHelper {

  private static final int MaxAttempts = 5;
  private static final int AwaitInMilliseconds = 1500;

  public static <T> T executeInTransaction(EntityManager entityManager, DatabaseOperation<T> operation) {
    EntityTransaction transaction = entityManager.getTransaction();
    boolean success = false;
    int maxAttempts = MaxAttempts;

    T result = null;

    while (!success && maxAttempts > 0) {
      try {
        transaction.begin();
        result = operation.execute(entityManager);
        transaction.commit();
        success = true;
      } catch (Exception e) {
        if (transaction != null && transaction.isActive()) {
          transaction.rollback();
        }
        maxAttempts--;
        try {
          System.out.println("[INFO]: Durmiendo, esperando liberar transacción");
          Thread.sleep(AwaitInMilliseconds);
        } catch (InterruptedException exception) {
          exception.printStackTrace();
        }
        if (maxAttempts == 0) {
          e.printStackTrace();
        }
      }
    }

    return result;
  }
}