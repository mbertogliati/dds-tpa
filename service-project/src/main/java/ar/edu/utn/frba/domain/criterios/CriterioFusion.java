package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.Organizacion;

public interface CriterioFusion {
    public boolean esFusionable(Organizacion org1, Organizacion org2);
}
