package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterioMinCantMeses implements CriterioFusion { //en Segundos
    private Long cantMinimaMeses;
    public CriterioMinCantMeses(Long cantMeses){
        this.cantMinimaMeses = cantMeses;
    }
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        boolean resultado = org1.getUltIntentoFusion().plusMonths(cantMinimaMeses).isBefore(LocalDate.now())
            && org2.getUltIntentoFusion().plusMonths(cantMinimaMeses).isBefore(LocalDate.now());
        if(!resultado){
            this.notificarError(org1, org2);
        }
        return resultado;
    }

    private void notificarError(Organizacion org1, Organizacion org2){
        System.out.println("No se pueden fusionar las organizaciones: " + org1.getId().toString() + " y " + org2.getId().toString() + ".\nMotivo: NO CUMPLE EL MÍNIMO DE MESES.");
    }
}