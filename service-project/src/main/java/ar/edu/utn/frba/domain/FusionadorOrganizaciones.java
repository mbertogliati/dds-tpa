package ar.edu.utn.frba.domain;

import ar.edu.utn.frba.domain.calculadorGradoConfianza.CalculadorGradoConfianza;
import ar.edu.utn.frba.domain.calculadorGradoConfianza.GradoCofianzaPromedioPonderado;
import ar.edu.utn.frba.domain.criterios.*;
import java.util.ArrayList;
import java.util.List;

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
            //TODO: AVERIGUAR COMO HACER GRADO DE CONFIANZA

            propuesta = new PropuestaFusion();

            propuesta.setIdOrganizacion1(org1.getId());
            propuesta.setIdOrganizacion2(org2.getId());
            propuesta.setOrganizacionFusionada(orgFusionada);
        }else{
            System.out.println("No se pueden fusionar las organizaciones: " + org1.getId().toString() + " y " + org2.getId().toString() + ".");

            //Implementar excepciones con mensaje de error OBLIGATORIO.

            //Una opcion puede ser que se lance una excepcion en cada implementacion de CriterioFusion
            //throw new RuntimeException("No se pueden fusionar las organizaciones");

            //Otra opcion puede ser que cada implementacion de CriterioFusion tenga un metodo que devuelva el mensaje de error
        }

        return propuesta;
    }
}
