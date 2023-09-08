package ar.edu.utn.frba.domain;

import ar.edu.utn.frba.domain.calculadorGradoConfianza.CalculadorGradoConfianza;
import ar.edu.utn.frba.domain.calculadorGradoConfianza.GradoCofianzaPromedioPonderado;
import ar.edu.utn.frba.domain.criterios.CriterioAnd;
import ar.edu.utn.frba.domain.criterios.CriterioFusion;
import ar.edu.utn.frba.domain.criterios.CriterioIgualGradoConfianza;
import ar.edu.utn.frba.domain.criterios.CriterioMinCantMeses;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeEstablecimientos;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeServicios;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeUsuarios;
import java.util.ArrayList;
import java.util.List;

public class RelacionadorOrganizaciones {

  public RelacionadorOrganizaciones() {
  }

  public List<OrganizacionesRelacionadas> getPosiblesFusiones(List<Organizacion> organizaciones, CriterioFusion criterio){
    List<OrganizacionesRelacionadas> posiblesFusiones = new ArrayList<OrganizacionesRelacionadas>();

    for(int i = 0; i < organizaciones.size() - 1; i++){
      for(int j = i + 1; j < organizaciones.size(); j++){
        OrganizacionesRelacionadas relacion = this.relacionar(organizaciones.get(i), organizaciones.get(j), criterio);
        if(relacion != null){
          posiblesFusiones.add(relacion);
        }
      }
    }

    return posiblesFusiones;
  }

  private OrganizacionesRelacionadas relacionar(Organizacion org1, Organizacion org2, CriterioFusion criterio){
    OrganizacionesRelacionadas relacionadas = null;

    if(criterio.esFusionable(org1, org2)){
      relacionadas = new OrganizacionesRelacionadas(org1, org2);
    }else{
      System.out.println("No se pueden fusionar las organizaciones: " + org1.getId().toString() + " y " + org2.getId().toString() + ".");

      //Implementar excepciones con mensaje de error OBLIGATORIO.

      //Una opcion puede ser que se lance una excepcion en cada implementacion de CriterioFusion
      //throw new RuntimeException("No se pueden fusionar las organizaciones");

      //Otra opcion puede ser que cada implementacion de CriterioFusion tenga un metodo que devuelva el mensaje de error
    }

    return relacionadas;
  }
}
