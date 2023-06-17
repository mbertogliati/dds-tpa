package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Denominacion;
import ar.edu.utn.frba.dds.domain.entidades.ControlEntidades;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;



public class ImportadorEntidadCSV implements ImportadorEntidadAdapter {

  private enum PosicionColumnasCSV {
    TIPO,
    ID,
    NOMBRE,
    DENOMINACION,
    ENTIDADES_RELACIONADAS
  }

  private final class TipoRecord {
    public static final String Entidad = "Entidad";
    public static final String Control = "Control";
    public static final String Prestador = "Prestador";
  }

  public List<ControlEntidades> importar(String path) {
    List<Entidad> entidadesTotales = new ArrayList<Entidad>();
    List<ControlEntidades> prestadoresYOrgDeControl = new ArrayList<ControlEntidades>();

    try (
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
    ) {

      List<CSVRecord> recordsSinEncabezado = csvParser.stream().filter(csvRecord -> csvRecord.getRecordNumber() > 0).toList();
      Stream<CSVRecord> streamEntidades = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Entidad));
      Stream<CSVRecord> streamPrestadoresYOrgDeControl = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Control) || csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Prestador));

      streamEntidades.forEach(csvRecord -> {
        entidadesTotales.add(crearEntidad(csvRecord));
      });

      streamPrestadoresYOrgDeControl.forEach(csvRecord -> {
        prestadoresYOrgDeControl.add(crearControlEntidades(csvRecord, entidadesTotales));
      });

    } catch (IOException e) {
      e.printStackTrace();
    }
    return prestadoresYOrgDeControl;
  }

  private Entidad crearEntidad(CSVRecord csvRecord) {
    Denominacion denominacion = new Denominacion(csvRecord.get(PosicionColumnasCSV.DENOMINACION));
    Entidad entidad = new Entidad(csvRecord.get(PosicionColumnasCSV.NOMBRE), denominacion);
    entidad.setId(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)));
    return entidad;
  }

  private ControlEntidades crearControlEntidades(CSVRecord csvRecord, List<Entidad> entidadesTotales) {
    Denominacion denominacion = new Denominacion(csvRecord.get(PosicionColumnasCSV.DENOMINACION));
    ControlEntidades controlEntidades = new ControlEntidades(csvRecord.get(PosicionColumnasCSV.NOMBRE), denominacion);
    List<Entidad> entidadesRelacionadas = obtenerEntidadesRelacionadas(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS), entidadesTotales);
    controlEntidades.getEntidades().addAll(entidadesRelacionadas);
    return controlEntidades;
  }

  private List<Entidad> obtenerEntidadesRelacionadas(String idEntidadesConcatenadas, List<Entidad> entidadesTotales) {
    List<Integer> idRelacionados = Arrays.stream(idEntidadesConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    return entidadesTotales.stream().filter(e -> idRelacionados.contains(e.getId())).toList();
  }
}
/*
* EJEMPLO .CSV
*    Tipo,Id,Nombre,Denominacion,Entidades Relacionadas
*    Control,1,CNRT,Organismo Estatal,"1,2,3"
*    Prestador,2,Trenes Argentinos, Empresa Estatal,"1"
*    Entidad,1,Línea Mitre,Linea de Tren,""
*    Prestador,3,Subterráneos de Buenos Aires,Empresa Estatal,"2,3"
*    Entidad,2,Línea B,Linea de Subte,""
*    Entidad,3,Línea C,Linea de Subte,""
* */
