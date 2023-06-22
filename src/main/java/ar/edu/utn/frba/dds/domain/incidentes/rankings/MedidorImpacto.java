package ar.edu.utn.frba.dds.domain.incidentes.rankings;

import ar.edu.utn.frba.dds.domain.comunidades.Comunidad;

public interface MedidorImpacto {
    public Comunidad comunidadConMasImpacto(Comunidad comunidadA, Comunidad comunidadB);
}
