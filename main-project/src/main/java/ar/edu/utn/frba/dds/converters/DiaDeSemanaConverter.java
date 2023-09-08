package ar.edu.utn.frba.dds.converters;

import java.time.DayOfWeek;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DiaDeSemanaConverter implements AttributeConverter<DayOfWeek, String> {
  @Override
  public String convertToDatabaseColumn(DayOfWeek dayOfWeek) {
    String dia = null;
    switch (dayOfWeek) {
      case MONDAY: dia = "Lunes"; break;
      case TUESDAY: dia = "Martes"; break;
      case WEDNESDAY: dia = "Miercoles"; break;
      case THURSDAY: dia = "Jueves"; break;
      case FRIDAY: dia = "Viernes"; break;
      case SATURDAY: dia = "Sabado"; break;
      case SUNDAY: dia = "Domingo"; break;
    }
    return dia;
  }

  @Override
  public DayOfWeek convertToEntityAttribute(String s) {
    DayOfWeek dia = null;
    if(s != null) {
      switch (s) {
        case "Lunes": dia = DayOfWeek.MONDAY; break;
        case "Martes": dia = DayOfWeek.TUESDAY; break;
        case "Miercoles": dia = DayOfWeek.WEDNESDAY; break;
        case "Jueves": dia = DayOfWeek.THURSDAY; break;
        case "Viernes": dia = DayOfWeek.FRIDAY; break;
        case "Sabado": dia = DayOfWeek.SATURDAY; break;
        case "Domingo": dia = DayOfWeek.MONDAY; break;
        default: throw new IllegalArgumentException(s + " no se puede convertir a un DayOfWeek");
      }
    }
    return dia;
  }
}
