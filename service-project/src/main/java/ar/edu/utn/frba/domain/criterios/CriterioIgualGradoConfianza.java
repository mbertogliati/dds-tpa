package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;

import ar.edu.utn.frba.exceptions.ErrorDeCriteriosException;
import java.util.Objects;

public class CriterioIgualGradoConfianza implements CriterioFusion{
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        boolean resultado = Objects.equals(org1.getGradoConfianza(), org2.getGradoConfianza());
        if(!resultado){
            this.notificarError(org1, org2);
        }
        return resultado;
    }

    private void notificarError(Organizacion org1, Organizacion org2){
        String tipoError = "No se pueden fusionar las organizaciones: " + org1.getIdOrganizacion().toString() + " y " + org2.getIdOrganizacion().toString() + ".";
        String mensaje = "Distinto grado de confianza.";
        System.out.println(tipoError);
        System.out.println(mensaje);
        throw new ErrorDeCriteriosException(tipoError, mensaje);
    }
}
