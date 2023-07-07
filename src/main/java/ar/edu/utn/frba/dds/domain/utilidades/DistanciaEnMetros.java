package ar.edu.utn.frba.dds.domain.utilidades;

public class DistanciaEnMetros implements DistanciaEntrePuntos{
    @Override
    public double distanciaEntre(Coordenada coordA, Coordenada coordB) {
        return org.apache.lucene.util.SloppyMath.haversinMeters(coordA.getLatitud(), coordA.getLongitud(), coordB.getLatitud(), coordB.getLongitud());
    }
}
