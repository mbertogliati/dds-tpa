package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;

public interface CriterioFusion {
    boolean esFusionable(Organizacion org1, Organizacion org2);
}