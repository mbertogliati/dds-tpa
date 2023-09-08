package ar.edu.utn.frba.domain;

import ar.edu.utn.frba.domain.calculadorGradoConfianza.CalculadorGradoConfianza;
import ar.edu.utn.frba.domain.criterios.*;
import ar.edu.utn.frba.domain.entidades.Organizacion;
import ar.edu.utn.frba.domain.entidades.OrganizacionesRelacionadas;
import ar.edu.utn.frba.domain.entidades.PropuestaFusion;

public class FusionadorOrganizaciones {

    public PropuestaFusion fusionar(OrganizacionesRelacionadas relacion, CriterioFusion criterioFusion, CalculadorGradoConfianza calculadorGradoConfianza){
        PropuestaFusion propuesta = null;

        Organizacion org1 = relacion.getOrganizacion1();
        Organizacion org2 = relacion.getOrganizacion2();

        if(criterioFusion.esFusionable(org1, org2)){
            Organizacion orgFusionada = new Organizacion();

            orgFusionada.getEstablecimientos().addAll(org1.getEstablecimientos());
            orgFusionada.getEstablecimientos().addAll(org2.getEstablecimientos());

            orgFusionada.getServicios().addAll(org1.getServicios());
            orgFusionada.getServicios().addAll(org2.getServicios());

            orgFusionada.getUsuarios().addAll(org1.getUsuarios());
            orgFusionada.getUsuarios().addAll(org2.getUsuarios());

            orgFusionada.setGradoConfianza(calculadorGradoConfianza.calcularGradoConfianza(org1, org2));

            propuesta = new PropuestaFusion();

            propuesta.setIdOrganizacion1(org1.getId());
            propuesta.setIdOrganizacion2(org2.getId());
            propuesta.setOrganizacionFusionada(orgFusionada);
        }

        return propuesta;
    }
}
