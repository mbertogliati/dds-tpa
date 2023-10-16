package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.fusion_organizacion.UltimoIntentoFusionComunidad;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class IntentoFusionComunidadRepositorio {
    private final EntityManager entityManager;

    public IntentoFusionComunidadRepositorio(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void guardarFusion(UltimoIntentoFusionComunidad fusion) {
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

    public void actualizarFusion(UltimoIntentoFusionComunidad fusion) {
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

    public void eliminarFusion(UltimoIntentoFusionComunidad fusion) {
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

    public List<UltimoIntentoFusionComunidad> obtenerTodas() {
        return entityManager.createQuery(
                        "SELECT e FROM " + UltimoIntentoFusionComunidad.class.getName() + " e", UltimoIntentoFusionComunidad.class)
                .getResultList();
    }
}
