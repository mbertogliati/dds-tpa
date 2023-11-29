package ar.edu.utn.frba.dds.server.jpa_extensions;

import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PerThreadEntityManagerAccessWithConfiguration {
  private final String persistenceUnitName;
  private final ConcurrentHashMap<String, EntityManagerFactory> emfHolder;
  private final ThreadLocal<EntityManager> threadLocal;
  private final PerThreadEntityManagerProperties properties;

  public PerThreadEntityManagerAccessWithConfiguration(String persistenceUnitName) {
    this.persistenceUnitName = persistenceUnitName;
    this.emfHolder = new ConcurrentHashMap(1);
    this.threadLocal = new ThreadLocal();
    this.properties = new PerThreadEntityManagerProperties();
  }

  public PerThreadEntityManagerAccessWithConfiguration(String persistenceUnitName, PerThreadEntityManagerProperties properties) {
    this.persistenceUnitName = persistenceUnitName;
    this.emfHolder = new ConcurrentHashMap(1);
    this.threadLocal = new ThreadLocal();
    this.properties = properties;
  }

  private void ensureNotInitialized() {
    if (this.emfHolder.containsKey(this.persistenceUnitName)) {
      throw new IllegalStateException("Can not set properties after initialization");
    }
  }

  public void configure(Consumer<PerThreadEntityManagerProperties> propertiesConsumer) {
    this.ensureNotInitialized();
    propertiesConsumer.accept(this.properties);
  }

  public EntityManagerFactory getEmf() {
    return (EntityManagerFactory)this.emfHolder.computeIfAbsent(this.persistenceUnitName, (name) -> {
      return Persistence.createEntityManagerFactory(name, this.properties.get());
    });
  }

  public void shutdown() {
    this.getEmf().close();
  }

  public boolean isActive() {
    return this.getEmf().isOpen();
  }

  private void ensureActive() {
    if (!this.getEmf().isOpen()) {
      throw new IllegalStateException("Can not get an entity manager before initialize or after shutdown");
    }
  }

  public EntityManager get() {
    this.ensureActive();
    EntityManager manager = (EntityManager)this.threadLocal.get();
    if (manager == null || !manager.isOpen()) {
      manager = this.getEmf().createEntityManager();
      this.threadLocal.set(manager);
    }

    return manager;
  }

  public boolean isAttached() {
    this.ensureActive();
    return this.threadLocal.get() != null;
  }

  public void dispose() {
    this.ensureActive();
    EntityManager em = (EntityManager)this.threadLocal.get();
    if (em != null) {
      em.close();
      this.threadLocal.remove();
    }

  }
}