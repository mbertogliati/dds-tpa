package ar.edu.utn.frba.dds.domain.servicios;

import ar.edu.utn.frba.dds.domain.utilidades.Etiqueta;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Servicio {
  @Getter @Setter
  private int id;
  @Getter
  private List<Etiqueta> etiquetas = new ArrayList<>();

  public Servicio(List<Etiqueta> etiquetas){
    this.etiquetas = etiquetas;
  }

  public void agregarEtiqueta(Etiqueta etiqueta){
    this.etiquetas.add(etiqueta);
  }

  public void eliminarEtiqueta(Etiqueta etiqueta){
    this.eliminarEtiquetaPorID(etiqueta.getId());
  }

  private void eliminarEtiquetaPorID(int id){
    for (Etiqueta etiqueta : etiquetas){
      if(etiqueta.getId() == id){
        etiquetas.remove(etiqueta);
      }
    }
  }
}
