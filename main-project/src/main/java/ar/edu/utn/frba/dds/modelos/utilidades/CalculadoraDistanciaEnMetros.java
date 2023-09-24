package ar.edu.utn.frba.dds.modelos.utilidades;

public class CalculadoraDistanciaEnMetros implements AdapterCalculadoraDistancia {
    @Override
    public double distanciaEntre(Coordenada coordenadaA, Coordenada coordenadaB) {
        return org.apache.lucene.util.SloppyMath.haversinMeters(coordenadaA.getLatitud(), coordenadaA.getLongitud(), coordenadaB.getLatitud(), coordenadaB.getLongitud());
    }
}
