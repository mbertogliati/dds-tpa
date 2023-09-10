package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;
import ar.edu.utn.frba.exceptions.ErrorDeCriteriosException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterioMinPorcentajeServicios implements CriterioFusion{
    private Double porcentajeMinimo;
    public CriterioMinPorcentajeServicios(Double porcentajeMinimo){
        this.porcentajeMinimo = porcentajeMinimo;
    }
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        long cant = org1.getServicios().stream()
                .filter(servicio -> org2.getServicios().contains(servicio))
                .count();

        boolean resultado = (double) cant /org1.getServicios().size() >= porcentajeMinimo
                && (double) cant /org2.getServicios().size() >= porcentajeMinimo;
        if(!resultado){
            this.notificarError(org1, org2);
        }
        return resultado;
    }

    private void notificarError(Organizacion org1, Organizacion org2){
        String tipoError = "No se pueden fusionar las organizaciones: " + org1.getId().toString() + " y " + org2.getId().toString() + ".";
        String mensaje = "No cumple con el porcentaje minimo de coincidencias de servicios.";
        System.out.println(tipoError);
        System.out.println(mensaje);
        throw new ErrorDeCriteriosException(tipoError, mensaje);
    }
}