package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Membresia {
  @Getter
  private Persona persona;
  @Getter
  private Comunidad comunidad;
  @Getter @Setter
  private Rol rolComunidad;
  @Getter
  private List<Servicio> serviciosObservados;

  public Membresia(Comunidad comunidad, Persona persona){
    this.persona = persona;
    this.comunidad = comunidad;
    serviciosObservados = new ArrayList<>();
  }

  public boolean estaAfectado(Servicio servicio){
    return !serviciosObservados.contains(servicio);
  }

}
