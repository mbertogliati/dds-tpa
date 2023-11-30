package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificablesParaCronTaskAlMomento;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.UltimoIntentoFusionComunidad;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class IntentoFusionComunidadRepositorio implements WithSimplePersistenceUnit {

    public void guardar(UltimoIntentoFusionComunidad fusion) {
        EntityTransaction transaction = entityManager().getTransaction();

        try {
            transaction.begin();
            entityManager().persist(fusion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizar(UltimoIntentoFusionComunidad fusion) {
        EntityTransaction transaction = entityManager().getTransaction();

        try {
            transaction.begin();
            entityManager().merge(fusion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminar(UltimoIntentoFusionComunidad fusion) {
        EntityTransaction transaction = entityManager().getTransaction();
        try {
            transaction.begin();
            entityManager().remove(fusion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<UltimoIntentoFusionComunidad> obtenerTodas() {
        return entityManager().createQuery(
                        "SELECT e FROM " + UltimoIntentoFusionComunidad.class.getName() + " e", UltimoIntentoFusionComunidad.class)
                .getResultList();
    }

    public void refresh(UltimoIntentoFusionComunidad ultimoIntentoFusionComunidad) {
        entityManager().refresh(ultimoIntentoFusionComunidad);
    }
}
