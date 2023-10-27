package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropuestaFusionComunidad {
  public PropuestaFusionComunidad(Comunidad c1, Comunidad c2){
    this.comunidad1 = c1;
    this.comunidad2 = c2;
  }
  private Comunidad comunidad1;
  private Comunidad comunidad2;

}
