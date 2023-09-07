package ar.edu.utn.frba.domain;

import ar.edu.utn.frba.domain.calculadorGradoConfianza.CalculadorGradoConfianza;
import ar.edu.utn.frba.domain.calculadorGradoConfianza.GradoCofianzaPromedioPonderado;
import ar.edu.utn.frba.domain.criterios.*;

public class FusionadorOrganizaciones {
    private CriterioFusion criterioFusion;
    private CalculadorGradoConfianza calculadorGradoConfianza;
    public FusionadorOrganizaciones() {
        CriterioAnd criterioAnd = new CriterioAnd();
        criterioAnd.agregarCriterios(
            new CriterioMinCantMeses(6L),
            new CriterioMinPorcentajeUsuarios(0.05),
            new CriterioMinPorcentajeEstablecimientos(0.75),
            new CriterioMinPorcentajeServicios(0.75),
            new CriterioIgualGradoConfianza()
        );
        this.criterioFusion = criterioAnd;
        calculadorGradoConfianza = new GradoCofianzaPromedioPonderado();
    }
    public PropuestaFusion fusionar(Organizacion org1, Organizacion org2){
        if(!criterioFusion.esFusionable(org1, org2)){
            throw new RuntimeException("No se pueden fusionar las organizaciones");
            //TODO: Implementar excepciones con mensaje de error OBLIGATORIO.
            //Una opcion puede ser que se lance una excepcion en cada implementacion de CriterioFusion
            //Otra opcion puede ser que cada implementacion de CriterioFusion tenga un metodo que devuelva el mensaje de error
        }
        Organizacion orgFusionada = new Organizacion();

        orgFusionada.getEstablecimientos().addAll(org1.getEstablecimientos());
        orgFusionada.getEstablecimientos().addAll(org2.getEstablecimientos());

        orgFusionada.getServicios().addAll(org1.getServicios());
        orgFusionada.getServicios().addAll(org2.getServicios());

        orgFusionada.getUsuarios().addAll(org1.getUsuarios());
        orgFusionada.getUsuarios().addAll(org2.getUsuarios());

        orgFusionada.setGradoConfianza(calculadorGradoConfianza.calcularGradoConfianza(org1, org2));

        PropuestaFusion propuesta = new PropuestaFusion();

        propuesta.setIdOrganizacion1(org1.getId());
        propuesta.setIdOrganizacion2(org2.getId());
        propuesta.setOrganizacionFusionada(orgFusionada);

        return propuesta;
    }
}
