package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;

public interface CriterioFusion {
    //TODO: Implementar mensajes de error para devolver por la API.
    boolean esFusionable(Organizacion org1, Organizacion org2);
}
