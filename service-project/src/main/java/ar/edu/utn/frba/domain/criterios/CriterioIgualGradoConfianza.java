package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.Organizacion;

import java.util.Objects;

public class CriterioIgualGradoConfianza implements CriterioFusion{
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        return Objects.equals(org1.getGradoConfianza(), org2.getGradoConfianza());
    }
}
