package ar.edu.utn.frba.domain.entidades;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Organizacion {
    private Long idOrganizacion;
    private Set<Long> establecimientos = new HashSet<>();
    private Set<Long> servicios = new HashSet<>();
    private Double gradoConfianza;
    private Set<Long> miembros = new HashSet<>();

    private Set<UltimoIntentoFusion> ultimosIntentosDeFusion = new HashSet<>();

    public LocalDateTime obtenerFechaIntentoCon(Organizacion unaOrganizacion) {
        try {
            return this.ultimosIntentosDeFusion
                .stream()
                .filter(f -> f.getIdOrganizacion() == unaOrganizacion.getIdOrganizacion()).findFirst().get()
                .getFechaIntento();
        }catch(NoSuchElementException e){
            return null;
        }
    }
}
