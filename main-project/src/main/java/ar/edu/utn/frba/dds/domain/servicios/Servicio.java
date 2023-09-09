package ar.edu.utn.frba.dds.domain.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "servicios")
@Getter
@Setter
public class Servicio {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "nombre")
  private String nombre;

  @ManyToMany
  private List<Etiqueta> etiquetas = new ArrayList<>();

  public Servicio(){}

  public Servicio(Etiqueta ... etiquetas){
    Collections.addAll(this.etiquetas, etiquetas);
  }

  public Servicio(List<Etiqueta> etiquetas){
    this.etiquetas.addAll(etiquetas);
  }

  public void agregarEtiqueta(Etiqueta etiqueta){
    this.etiquetas.add(etiqueta);
  }

  public void eliminarEtiqueta(Etiqueta etiqueta){
    this.eliminarEtiquetaPorID(etiqueta.getId());
  }

  public String getStringEtiquetas(){
    List<String> listaEtiquetas = this.etiquetas.stream().map(e -> e.getValor()).toList();
    String texto = "";
    for (String etiqueta : listaEtiquetas){
      texto = texto + etiqueta + " - ";
    }
    return texto;
  }
  private void eliminarEtiquetaPorID(int id){
    for (Etiqueta etiqueta : this.etiquetas){
      if(etiqueta.getId() == id){
        this.etiquetas.remove(etiqueta);
      }
    }
  }
}
