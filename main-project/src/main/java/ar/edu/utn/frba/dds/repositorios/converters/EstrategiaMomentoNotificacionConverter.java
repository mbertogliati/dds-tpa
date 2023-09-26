package ar.edu.utn.frba.dds.repositorios.converters;

import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.EstrategiaMomentoNotificacion;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificacionAlMomento;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificacionSinApuro;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EstrategiaMomentoNotificacionConverter implements AttributeConverter<EstrategiaMomentoNotificacion, String> {
  @Override
  public String convertToDatabaseColumn(EstrategiaMomentoNotificacion medioDeNotificacion) {
    if(medioDeNotificacion == null)
      return "alMomento";

    String medioEnBase = null;

    if(medioDeNotificacion.getClass().getName().equals("NotificacionAlMomento")) {
      medioEnBase = "alMomento";
    }
    else if(medioDeNotificacion.getClass().getName().equals("NotificacionSinApuro")) {
      medioEnBase = "sinApuro";
    }

    return medioEnBase;
  }

  @Override
  public EstrategiaMomentoNotificacion convertToEntityAttribute(String s) {
    EstrategiaMomentoNotificacion medio = null;

    if(s == null) return new NotificacionAlMomento();

    if(s.equals("alMomento")) {
      medio = new NotificacionAlMomento();
    }
    else if(s.equals("sinApuro")) {
      medio = new NotificacionSinApuro();
    }
    return medio;
  }
}
