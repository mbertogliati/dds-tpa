package ar.edu.utn.frba.dds.modelos.servicios;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "servicios")

@Getter
@Setter
public class Servicio extends ModelBase {
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
    String texto = this.nombre + " | ";
    for (String etiqueta : listaEtiquetas){
      texto = texto + etiqueta + " - ";
    }
    if(texto != ""){
      texto = texto.substring(0, texto.length() - 3);
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
