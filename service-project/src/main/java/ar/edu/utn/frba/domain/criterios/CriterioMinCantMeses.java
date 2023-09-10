package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;
import ar.edu.utn.frba.exceptions.ErrorDeCriteriosException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        LocalDateTime fecha1 = org1.obtenerFechaIntentoCon(org2);
        LocalDateTime fecha2 = org2.obtenerFechaIntentoCon(org1);


        boolean resultado =
            (fecha1 == null || fecha1.plusMonths(cantMinimaMeses).isBefore(LocalDateTime.now())) &&
                (fecha2 == null || fecha2.plusMonths(cantMinimaMeses).isBefore(LocalDateTime.now()));

        if(!resultado){
            this.notificarError(org1, org2);
        }
        return resultado;
    }

    private void notificarError(Organizacion org1, Organizacion org2){
        String tipoError = "No se pueden fusionar las organizaciones: " + org1.getId().toString() + " y " + org2.getId().toString() + ".";
        String mensaje = "No cumple con el tiempo minimo desde el ultimo intento de fusion.";
        System.out.println(tipoError);
        System.out.println(mensaje);
        throw new ErrorDeCriteriosException(tipoError, mensaje);
    }
}