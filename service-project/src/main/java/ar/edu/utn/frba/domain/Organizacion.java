package ar.edu.utn.frba.domain;

import ar.edu.utn.frba.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.datatype.jsr310.*;
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
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate ultIntentoFusion;
}
