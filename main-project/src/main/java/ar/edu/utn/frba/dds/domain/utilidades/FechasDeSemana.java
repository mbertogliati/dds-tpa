package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.converters.DiaDeSemanaConverter;
import java.time.DayOfWeek;
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
@Table(name = "fechasDeSemana")
public class FechasDeSemana {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Convert(converter = DiaDeSemanaConverter.class)
  @Column(name = "dia")
  private DayOfWeek dia;

  @Column(name = "horario", columnDefinition = "TIME")
  private LocalTime horario;
}
