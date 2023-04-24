package ar.edu.utn.frba.dds.domain.services;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Servicio {
  @Setter
  private int id;
  @Setter
  private TipoServicio tipo;
  @Setter @Getter
  private List<SubtipoServicio> subtipos;

  public Servicio() {
    this.id = 0;
    this.tipo = null;
    this.subtipos = new ArrayList<SubtipoServicio>();
  }

  public Servicio(int id, TipoServicio tipo, List<SubtipoServicio> subtipos) {
    this.id = id;
    this.tipo = tipo;
    this.subtipos = subtipos;
  }

  public void agregarSubtipo(SubtipoServicio nuevoSubtipo) {
    this.subtipos.add(nuevoSubtipo);
  }

  public void eliminarSubtipo(SubtipoServicio subtipoServicio) {
    for (SubtipoServicio subtipoActual : this.subtipos) {
      if (subtipoActual.getId() == subtipoServicio.getId()) {
        this.subtipos.remove(subtipoActual);
      }
    }
  }

  public void eliminarSubtipo(int idBuscada) {
    for (SubtipoServicio subtipoActual : this.subtipos) {
      if (subtipoActual.getId() == idBuscada) {
        this.subtipos.remove(subtipoActual);
      }
    }
  }
}
