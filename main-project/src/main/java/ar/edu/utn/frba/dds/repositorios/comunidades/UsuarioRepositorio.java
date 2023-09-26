package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class UsuarioRepositorio {

  private final EntityManager entityManager;

  public UsuarioRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Usuario usuario) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(usuario);
    transaction.commit();
  }

  public Usuario buscarPorId(int id) {
    return entityManager.find(Usuario.class, id);
  }

  public void actualizar(Usuario usuario) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(usuario);
    transaction.commit();
  }

  public void eliminar(Usuario usuario) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(usuario);
    transaction.commit();
  }

  public List<Usuario> buscarTodos() {
    TypedQuery<Usuario> query = entityManager.createQuery("FROM " + Usuario.class.getName(), Usuario.class);
    return query.getResultList();
  }

  public List<Usuario> buscarPorUsername(String username) {
    return (List<Usuario>) entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.username = :usuarioBuscado")
        .setParameter("usuarioBuscado", username)
        .getResultList();
  }
}