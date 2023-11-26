package ar.edu.utn.frba.dds.controllers.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public enum TipoRol{
    DEFAULT,
    ADMINISTRADOR,
    ADMINISTRADOR_COMUNIDAD,
    DEFAULT_COMUNIDAD;

    public String nombreLindo(){
        return Pattern.compile("^.| .").matcher(this.name().replace("_"," ").toLowerCase()).replaceAll(m -> m.group().toUpperCase());
    }
}
