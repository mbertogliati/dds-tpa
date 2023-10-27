package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;
import ar.edu.utn.frba.exceptions.ErrorDeCriteriosException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterioMinPorcentajeEstablecimientos implements CriterioFusion{
    private Double porcentajeMinimo;
    public CriterioMinPorcentajeEstablecimientos(Double porcentajeMinimo){
        this.porcentajeMinimo = porcentajeMinimo;
    }
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        long cant = org1.getEstablecimientos().stream()
                .filter(establecimiento -> org2.getEstablecimientos().contains(establecimiento))
                .count();

        boolean resultado = (double) cant /org1.getEstablecimientos().size() >= porcentajeMinimo
                && (double) cant /org2.getEstablecimientos().size() >= porcentajeMinimo;
        if(!resultado){
            this.notificarError(org1, org2);
        }
        return resultado;
    }

    private void notificarError(Organizacion org1, Organizacion org2){
        String tipoError = "No se pueden fusionar las organizaciones: " + org1.getIdOrganizacion().toString() + " y " + org2.getIdOrganizacion().toString() + ".";
        String mensaje = "No cumple con el porcentaje minimo de coincidencias de establecimientos.";
        System.out.println(tipoError);
        System.out.println(mensaje);
        throw new ErrorDeCriteriosException(tipoError, mensaje);
    }
}
