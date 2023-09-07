package ar.edu.utn.frba.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Organizacion {
    private Long id;
    private Set<Long> establecimientos = new HashSet<>();
    private Set<Long> servicios = new HashSet<>();
    private Double gradoConfianza;
    private Set<Long> usuarios = new HashSet<>();
    private LocalDateTime ultIntentoFusion;
}
