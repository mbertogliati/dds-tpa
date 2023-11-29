package ar.edu.utn.frba.dds.modelos.utilidades;

import ar.edu.utn.frba.dds.repositorios.converters.DiaDeSemanaConverter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fechas_de_semana")
public class FechasDeSemana {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Convert(converter = DiaDeSemanaConverter.class)
  @Column(name = "dia")
  private DayOfWeek dia;

  @Column(name = "horario", columnDefinition = "TIME")
  private LocalTime horario;

  public FechasDeSemana(){}

  public FechasDeSemana(DayOfWeek dia, LocalTime horario){
    this.dia = dia;
    this.horario = horario;
  }
  public boolean sePuedeNotificar(LocalDateTime fechaHora){
    return fechaHora.getDayOfWeek().equals(this.dia) && fechaHora.getHour() == this.horario.getHour() && fechaHora.getMinute() == this.horario.getMinute();
  }
}
