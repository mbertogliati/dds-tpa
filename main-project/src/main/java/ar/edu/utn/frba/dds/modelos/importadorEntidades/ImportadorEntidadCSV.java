package ar.edu.utn.frba.dds.modelos.importadorEntidades;

import ar.edu.utn.frba.dds.modelos.entidades.Denominacion;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ImportadorEntidadCSV implements ImportadorEntidadAdapter {
  private enum PosicionColumnasCSV {
    TIPO,
    ID,
    NOMBRE,
    DENOMINACION,
    ENTIDADES_RELACIONADAS
  }

  private static final class TipoRecord {
    public static final String Entidad = "Entidad";
    public static final String Control = "Control";
    public static final String Prestador = "Prestador";
  }

  public List<OrganismoControl> importar(String path) {
    List<Entidad> entidades = new ArrayList<Entidad>();
    List<EntidadPrestadora> entidadesPrestadoras = new ArrayList<EntidadPrestadora>();
    List<OrganismoControl> organismosDeControl = new ArrayList<OrganismoControl>();

    try (
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
    ) {

      List<CSVRecord> recordsSinEncabezado = csvParser.stream().filter(csvRecord -> csvRecord.getRecordNumber() > 0).toList();
      Stream<CSVRecord> streamEntidades = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Entidad));
      Stream<CSVRecord> streamEntidadesPrestadoras = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Prestador));
      Stream<CSVRecord> streamOrganismosDeControl = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Control));

      streamEntidades.forEach(csvRecord -> {
        entidades.add(crearEntidad(csvRecord));
      });

      streamEntidadesPrestadoras.forEach(csvRecord -> {
        entidadesPrestadoras.add(crearEntidadPrestadora(csvRecord, entidades));
      });

      streamOrganismosDeControl.forEach(csvRecord -> {
        organismosDeControl.add(crearControlEntidades(csvRecord, entidadesPrestadoras));
      });

    } catch (IOException e) {
      e.printStackTrace();
    }
    return organismosDeControl;
  }

  private EntidadPrestadora crearEntidadPrestadora(CSVRecord csvRecord, List<Entidad> entidades) {
    EntidadPrestadora entidadPrestadora = new EntidadPrestadora(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    List<Entidad> entidadesRelacionadas = obtenerEntidadesRelacionadas(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS), entidades);
    entidadPrestadora.setId(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)));
    entidadPrestadora.setEntidades(entidadesRelacionadas);
    return entidadPrestadora;
  }

  private OrganismoControl crearControlEntidades(CSVRecord csvRecord, List<EntidadPrestadora> entidadesPrestadoras) {
    OrganismoControl organismoControl = new OrganismoControl(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    List<EntidadPrestadora> entidadesPrestadorasRelacionadas = obtenerEntidadesPrestadorasRelacionadas(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS), entidadesPrestadoras);
    organismoControl.getEntidadesPrestadoras().addAll(entidadesPrestadorasRelacionadas);
    return organismoControl;
  }
  private List<Entidad> obtenerEntidadesRelacionadas(String idEntidadesConcatenadas, List<Entidad> entidades) {
    List<Integer> idRelacionados = Arrays.stream(idEntidadesConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    return entidades.stream().filter(e -> idRelacionados.contains(e.getId())).toList();
  }
  private List<EntidadPrestadora> obtenerEntidadesPrestadorasRelacionadas(String idEntidadesConcatenadas, List<EntidadPrestadora> entidadesTotales) {
    List<Integer> idRelacionados = Arrays.stream(idEntidadesConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    return entidadesTotales.stream().filter(e -> idRelacionados.contains(e.getId())).toList();
  }

  private Entidad crearEntidad(CSVRecord csvRecord) {
    Entidad entidad = new Entidad();
    entidad.setId(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)));
    entidad.setNombre(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    entidad.setDenominacion(new Denominacion(csvRecord.get(PosicionColumnasCSV.DENOMINACION)));
    return entidad;
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