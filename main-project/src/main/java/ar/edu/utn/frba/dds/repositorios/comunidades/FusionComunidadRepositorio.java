package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.FusionComunidad;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class FusionComunidadRepositorio {
    private final EntityManager entityManager;

    public FusionComunidadRepositorio(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void guardarFusion(FusionComunidad fusion) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(fusion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizarFusion(FusionComunidad fusion) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(fusion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminarFusion(FusionComunidad fusion) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(fusion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<FusionComunidad> obtenerTodas() {
        return entityManager.createQuery(
                        "SELECT e FROM " + FusionComunidad.class.getName() + " e", FusionComunidad.class)
                .getResultList();
    }
}
