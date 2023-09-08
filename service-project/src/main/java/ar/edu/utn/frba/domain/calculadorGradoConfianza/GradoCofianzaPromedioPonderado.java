package ar.edu.utn.frba.domain.calculadorGradoConfianza;

import ar.edu.utn.frba.domain.entidades.Organizacion;

public class GradoCofianzaPromedioPonderado implements CalculadorGradoConfianza{
    @Override
    public Double calcularGradoConfianza(Organizacion org1, Organizacion org2) {
        return (    org1.getGradoConfianza()*org1.getUsuarios().size()
                  + org2.getGradoConfianza()*org2.getUsuarios().size())
                / (org1.getUsuarios().size() +org2.getUsuarios().size());
    }
}
