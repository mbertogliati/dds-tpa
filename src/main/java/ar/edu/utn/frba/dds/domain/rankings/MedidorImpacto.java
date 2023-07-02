package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.comunidades.Comunidad;

public interface MedidorImpacto {
    public Comunidad comunidadConMasImpacto(Comunidad comunidadA, Comunidad comunidadB);
}
