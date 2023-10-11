package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo;

import ar.edu.utn.frba.dds.modelos.fusion_organizacion.FusionCompletada;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.Organizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.PropuestaFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.SolicitudFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.UltimoIntentoFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.RequestOrganizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.RequestSolicitudFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.RequestUltimoIntentoFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.ResponseFusionCompletada;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.ResponsePropuestaFusion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MapperFusion {
  public static RequestOrganizacion mapOrganizacionAFusionarARequestOrganizacion(Organizacion organizacion) {
    RequestOrganizacion request = new RequestOrganizacion();
    request.setIdOrganizacion(organizacion.getIdOrganizacion());
    request.setServicios(organizacion.getMiembros());
    request.setMiembros(organizacion.getMiembros());
    request.setEstablecimientos(organizacion.getEstablecimientos());
    request.setGradoConfianza(organizacion.getGradoConfianza());
    request.setUltimosIntentosDeFusion(organizacion.getUltimosIntentosDeFusion().stream().map(MapperFusion::mapUltimoIntentoFusionARequestUltimoIntentoFusion).collect(Collectors.toSet()));
    return request;
  }

  public static RequestUltimoIntentoFusion mapUltimoIntentoFusionARequestUltimoIntentoFusion(UltimoIntentoFusion ultimoIntentoFusion) {
    RequestUltimoIntentoFusion request = new RequestUltimoIntentoFusion();
    request.setFechaIntento(ultimoIntentoFusion.getFechaIntento().toString());
    request.setIdOrganizacion(ultimoIntentoFusion.getIdOrganizacion());
    return request;
  }

  public static PropuestaFusion mapResponsePropuestaFusionAPropuestaFusion(ResponsePropuestaFusion responsePropuestaFusion) {
    PropuestaFusion propuesta = new PropuestaFusion();
    propuesta.setIdOrganizacion1(responsePropuestaFusion.getIdOrganizacion1());
    propuesta.setIdOrganizacion2(responsePropuestaFusion.getIdOrganizacion2());
    return propuesta;
  }

  public static RequestSolicitudFusion mapSolicitudFusionARequestSolicitudFusion(SolicitudFusion solicitud) {
    RequestSolicitudFusion request = new RequestSolicitudFusion();
    request.setOrganizacion1(mapOrganizacionAFusionarARequestOrganizacion(solicitud.getOrganizacion1()));
    request.setOrganizacion2(mapOrganizacionAFusionarARequestOrganizacion(solicitud.getOrganizacion2()));
    return request;
  }

  public static FusionCompletada mapResponseFusionCompletadaAFusionCompletada(ResponseFusionCompletada responseFusionCompletada) {
    FusionCompletada fusionCompletada = new FusionCompletada();
    fusionCompletada.setIdOrganizacion1(responseFusionCompletada.getIdOrganizacion1());
    fusionCompletada.setIdOrganizacion2(responseFusionCompletada.getIdOrganizacion2());
    fusionCompletada.setOrganizacionFusionada(mapRequestOrganizacionAOrganizacion(responseFusionCompletada.getOrganizacionFusionada()));
    return fusionCompletada;
  }

  public static Organizacion mapRequestOrganizacionAOrganizacion(RequestOrganizacion requestOrganizacion) {
    Organizacion organizacion = new Organizacion(); //TODO: completar
    organizacion.setIdOrganizacion(requestOrganizacion.getIdOrganizacion());
    organizacion.setServicios(requestOrganizacion.getMiembros());
    organizacion.setMiembros(requestOrganizacion.getMiembros());
    organizacion.setEstablecimientos(requestOrganizacion.getEstablecimientos());
    organizacion.setGradoConfianza(requestOrganizacion.getGradoConfianza());
    organizacion.setUltimosIntentosDeFusion(requestOrganizacion.getUltimosIntentosDeFusion().stream().map(MapperFusion::mapRequestUltimoIntentoFusionAUltimoIntentoFusion).collect(Collectors.toSet()));
    return organizacion;
  }

  public static UltimoIntentoFusion mapRequestUltimoIntentoFusionAUltimoIntentoFusion(RequestUltimoIntentoFusion requestUltimoIntentoFusion) {
    UltimoIntentoFusion ultimoIntentoFusion = new UltimoIntentoFusion();
    ultimoIntentoFusion.setFechaIntento(mapStringDateToLocalDateTime(requestUltimoIntentoFusion.getFechaIntento()));
    ultimoIntentoFusion.setIdOrganizacion(requestUltimoIntentoFusion.getIdOrganizacion());
    return ultimoIntentoFusion;
  }

  public static LocalDateTime mapStringDateToLocalDateTime(String stringDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime localDateTime = LocalDateTime.parse(stringDate, formatter);
    return localDateTime;
  }
}
