package ar.edu.utn.frba.domain.calculadorGradoConfianza;

import ar.edu.utn.frba.domain.entidades.Organizacion;

public class GradoCofianzaPromedioPonderado implements CalculadorGradoConfianza{
    @Override
    public Double calcularGradoConfianza(Organizacion org1, Organizacion org2) {
        return (    org1.getGradoConfianza()*org1.getMiembros().size()
                  + org2.getGradoConfianza()*org2.getMiembros().size())
                / (org1.getMiembros().size() +org2.getMiembros().size());
    }
}
