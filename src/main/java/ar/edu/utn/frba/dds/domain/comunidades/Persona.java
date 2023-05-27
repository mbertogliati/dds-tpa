package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Persona {
  @Getter
  private Usuario usuario;
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private String apellido;
  @Getter @Setter
  private String email;
  @Getter
  private Interes interes = null;
  @Getter
  private List<Membresia> membresias = new ArrayList<>();

  public Persona(String nombre, String apellido, String email, Usuario usuario){
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
  }

  public void agregarMembresia(Membresia membresia){
    this.membresias.add(membresia);
    membresia.getComunidad().agregarMembresiaDirecto(membresia);
  }

  public void agregarMembresiaDirecto(Membresia membresia){
    this.membresias.add(membresia);
  }

  public void eliminarMembresiaDirecto(Membresia membresia){
    this.membresias.remove(membresia);
  }

  public void eliminarMembresia(Membresia membresia){
    this.membresias.remove(membresia);
    membresia.getComunidad().eliminarMembresiaDirecto(membresia);
  }

  public void eliminarMembresiaPorComunidad(Comunidad comunidad){
    for (Membresia membresia : membresias){
      if(membresia.getComunidad().getId() == comunidad.getId()){
        this.membresias.remove(membresia);
        membresia.getComunidad().eliminarMembresiaDirecto(membresia);
      }
    }
  }

}
