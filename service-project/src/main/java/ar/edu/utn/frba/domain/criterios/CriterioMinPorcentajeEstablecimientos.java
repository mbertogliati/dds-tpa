package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.Organizacion;
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
        return (double) cant /org1.getEstablecimientos().size() >= porcentajeMinimo
                && (double) cant /org2.getEstablecimientos().size() >= porcentajeMinimo;
    }
}
