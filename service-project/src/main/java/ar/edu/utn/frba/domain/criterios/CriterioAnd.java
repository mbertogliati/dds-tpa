package ar.edu.utn.frba.domain.criterios;

import ar.edu.utn.frba.domain.entidades.Organizacion;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriterioAnd implements CriterioFusion{
    private List<CriterioFusion> criterios;

    public CriterioAnd(){
        this.criterios = new ArrayList<CriterioFusion>();
    }

    public void agregarCriterios(CriterioFusion...criterios){
        this.criterios.addAll(List.of(criterios));
    }
    @Override
    public boolean esFusionable(Organizacion org1, Organizacion org2) {
        return criterios.stream().allMatch(criterio -> criterio.esFusionable(org1, org2));
    }
}
