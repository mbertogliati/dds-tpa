package ar.edu.utn.frba.domain;

import ar.edu.utn.frba.domain.criterios.CriterioFusion;
import ar.edu.utn.frba.domain.entidades.Organizacion;
import ar.edu.utn.frba.domain.entidades.OrganizacionesRelacionadas;
import ar.edu.utn.frba.exceptions.ErrorDeCriteriosException;
import java.util.ArrayList;
import java.util.List;

public class RelacionadorOrganizaciones {

  public RelacionadorOrganizaciones() {
  }

  public List<OrganizacionesRelacionadas> getPosiblesFusiones(List<Organizacion> organizaciones, CriterioFusion criterio){
    List<OrganizacionesRelacionadas> posiblesFusiones = new ArrayList<OrganizacionesRelacionadas>();

    for(int i = 0; i < organizaciones.size() - 1; i++){

      Organizacion organizacion1 = organizaciones.get(i);
      if(posiblesFusiones.stream().allMatch(pf -> pf.getOrganizacion1().getIdOrganizacion() != organizacion1.getIdOrganizacion() && pf.getOrganizacion2().getIdOrganizacion() != organizacion1.getIdOrganizacion())){
        //Si la primer organizacion de la relación no está en las posibles fusiones

        for(int j = i + 1; j < organizaciones.size(); j++){
          if(!posiblesFusiones.stream().allMatch(pf -> pf.getOrganizacion1().getIdOrganizacion() != organizacion1.getIdOrganizacion() && pf.getOrganizacion2().getIdOrganizacion() != organizacion1.getIdOrganizacion())){
            //Si la primer organización de la relación ahora está en las posibles fusiones, salgo del for
            break;
          }

          Organizacion organizacion2 = organizaciones.get(j);

          if(posiblesFusiones.stream().allMatch(pf -> pf.getOrganizacion1().getIdOrganizacion() != organizacion1.getIdOrganizacion() && pf.getOrganizacion2().getIdOrganizacion() != organizacion2.getIdOrganizacion())){
            //Si la segunda organizacion de la relación no está en las posibles fusiones

            OrganizacionesRelacionadas relacion = this.relacionar(organizaciones.get(i), organizaciones.get(j), criterio);

            if(relacion != null){
              //Si la relacion se pudo realizar
              posiblesFusiones.add(relacion);
            }

          }

        }

      }

    }

    return posiblesFusiones;
  }

  private OrganizacionesRelacionadas relacionar(Organizacion org1, Organizacion org2, CriterioFusion criterio){
    OrganizacionesRelacionadas relacionadas = null;

    try{
      criterio.esFusionable(org1, org2);
      relacionadas = new OrganizacionesRelacionadas(org1, org2);
    } catch(ErrorDeCriteriosException e){

    }


    return relacionadas;
  }
}
