package ar.edu.utn.frba.domain.entidades;

import ar.edu.utn.frba.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Organizacion {
    private Long id;
    private Set<Long> establecimientos = new HashSet<>();
    private Set<Long> servicios = new HashSet<>();
    private Double gradoConfianza;
    private Set<Long> miembros = new HashSet<>();

    private Set<UltimoIntentoFusion> ultimosIntentosDeFusion = new HashSet<>();

    public LocalDateTime obtenerFechaIntentoCon(Organizacion unaOrganizacion) {
        return this.ultimosIntentosDeFusion
            .stream()
            .filter(f -> f.getOrganizacionId() == unaOrganizacion.getId()).findFirst().get()
            .getFechaIntento();
    }
}
