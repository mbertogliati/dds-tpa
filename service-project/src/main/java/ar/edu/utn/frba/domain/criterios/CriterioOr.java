package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.Organizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriterioOr implements CriterioFusion{
    private List<CriterioFusion> criterios;
    public void agregarCriterios(CriterioFusion...criterios){
        this.criterios.addAll(List.of(criterios));
    }
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        return criterios.stream().anyMatch(criterio -> criterio.esFusionable(org1, org2));
    }
}