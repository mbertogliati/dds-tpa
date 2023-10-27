package ar.edu.utn.frba.domain.calculadorGradoConfianza;

import ar.edu.utn.frba.domain.entidades.Organizacion;

public interface CalculadorGradoConfianza {
    Double calcularGradoConfianza(Organizacion org1, Organizacion org2);
}
