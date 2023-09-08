package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.Organizacion;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CriterioMinCantMeses implements CriterioFusion { //en Segundos
    private Long cantMinimaMeses;
    public CriterioMinCantMeses(Long cantMeses){
        this.cantMinimaMeses = cantMeses;
    }
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
    return org1.getUltIntentoFusion().plusMonths(cantMinimaMeses).isAfter(LocalDate.now())
        && org2.getUltIntentoFusion().plusMonths(cantMinimaMeses).isAfter(LocalDate.now());
    }
}