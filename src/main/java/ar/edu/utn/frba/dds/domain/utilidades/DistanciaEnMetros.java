package ar.edu.utn.frba.dds.domain.utilidades;

public class DistanciaEnMetros implements DistanciaEntrePuntos{
    @Override
    public double distanciaEntre(Ubicacion ubicacionA, Ubicacion ubicacionB) {
        return org.apache.lucene.util.SloppyMath.haversinMeters(ubicacionA.getLatitud(), ubicacionA.getLongitud(), ubicacionB.getLatitud(), ubicacionB.getLongitud());
    }
}
