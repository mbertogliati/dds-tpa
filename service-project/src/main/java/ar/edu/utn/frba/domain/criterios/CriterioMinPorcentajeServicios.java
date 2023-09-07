package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.Organizacion;
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
        return (double) cant /org1.getServicios().size() >= porcentajeMinimo
                && (double) cant /org2.getServicios().size() >= porcentajeMinimo;
    }
}