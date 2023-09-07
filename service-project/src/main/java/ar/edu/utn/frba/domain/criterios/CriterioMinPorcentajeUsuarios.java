package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.Organizacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterioMinPorcentajeUsuarios implements CriterioFusion {
    private Double porcentajeMinimo;
    public CriterioMinPorcentajeUsuarios(Double porcentajeMinimo){
        this.porcentajeMinimo = porcentajeMinimo;
    }
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        long cant = org1.getUsuarios().stream()
                .filter(usaurio -> org2.getUsuarios().contains(usaurio))
                .count();
        return (double) cant / org1.getUsuarios().size() >= porcentajeMinimo
                && (double) cant / org2.getUsuarios().size() >= porcentajeMinimo;
    }
}
