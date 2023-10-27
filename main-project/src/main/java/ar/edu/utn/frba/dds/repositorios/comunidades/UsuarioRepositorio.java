package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
    usuario.setActivo(false);
    this.actualizar(usuario);
    //entityManager.clear();
  }

  public List<Usuario> buscarTodos() {
    return entityManager.createQuery(
            "SELECT e FROM " + Usuario.class.getName() + "  e WHERE e.activo=TRUE", Usuario.class)
        .getResultList();
  }

  public List<Usuario> buscarPorUsername(String username) {
    return entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.username LIKE :usuarioBuscado", Usuario.class)
        .setParameter("usuarioBuscado", "%" + username + "%")
        .getResultList();
  }
}