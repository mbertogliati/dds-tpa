package ar.edu.utn.frba.domain.entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizacionesRelacionadas {
  private Organizacion organizacion1;
  private Organizacion organizacion2;

  public OrganizacionesRelacionadas(Organizacion org1, Organizacion org2){
    this.organizacion1 = org1;
    this.organizacion2 = org2;
  }

  public OrganizacionesRelacionadas(){}

  public IdDeOrganizacionesRelacionadas getIds(){
    IdDeOrganizacionesRelacionadas respuesta = new IdDeOrganizacionesRelacionadas();
    respuesta.setIdOrganizacion1(this.organizacion1.getIdOrganizacion());
    respuesta.setIdOrganizacion2(this.organizacion2.getIdOrganizacion());
    return respuesta;
  }
}
