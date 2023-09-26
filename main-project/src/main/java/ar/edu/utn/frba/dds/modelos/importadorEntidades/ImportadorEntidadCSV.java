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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private Map<Integer, Entidad> entidades = new HashMap<>();
  private Map<Integer, EntidadPrestadora> entidadesPrestadoras = new HashMap<>();
  List<OrganismoControl> organismosDeControl = new ArrayList<OrganismoControl>();
  public List<OrganismoControl> importar(String path) {

    //List<Entidad> entidades = new ArrayList<Entidad>();
    //List<EntidadPrestadora> entidadesPrestadoras = new ArrayList<EntidadPrestadora>();
    //List<OrganismoControl> organismosDeControl = new ArrayList<OrganismoControl>();

    try (
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
    ) {

      List<CSVRecord> recordsSinEncabezado = csvParser.stream().filter(csvRecord -> csvRecord.getRecordNumber() > 0).toList();
      Stream<CSVRecord> streamEntidades = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Entidad));
      Stream<CSVRecord> streamEntidadesPrestadoras = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Prestador));
      Stream<CSVRecord> streamOrganismosDeControl = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Control));

      streamEntidades.forEach(this::crearEntidad);

      streamEntidadesPrestadoras.forEach(this::crearEntidadPrestadora);

      streamOrganismosDeControl.forEach(this::crearOrganismosControl);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return organismosDeControl;
  }

  private void crearEntidadPrestadora(CSVRecord csvRecord) {
    EntidadPrestadora entidadPrestadora = new EntidadPrestadora(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    List<Entidad> entidadesRelacionadas = obtenerEntidadesRelacionadas(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS));
    //entidadPrestadora.setId(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)));
    entidadPrestadora.setEntidades(entidadesRelacionadas);
    entidadesPrestadoras.put(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)), entidadPrestadora);
  }

  private void crearOrganismosControl(CSVRecord csvRecord) {
    OrganismoControl organismoControl = new OrganismoControl(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    List<EntidadPrestadora> entidadesPrestadorasRelacionadas = obtenerEntidadesPrestadorasRelacionadas(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS));
    organismoControl.getEntidadesPrestadoras().addAll(entidadesPrestadorasRelacionadas);
    this.organismosDeControl.add(organismoControl);
  }
  private List<Entidad> obtenerEntidadesRelacionadas(String idEntidadesConcatenadas) {
    List<Integer> idRelacionados = Arrays.stream(idEntidadesConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    List<Entidad> entidadesAux = new ArrayList<>();
    for(Integer id : idRelacionados) {
      entidadesAux.add(this.entidades.get(id));
    }
    return entidadesAux;
  }
  private List<EntidadPrestadora> obtenerEntidadesPrestadorasRelacionadas(String idEntidadesConcatenadas) {
    List<Integer> idRelacionados = Arrays.stream(idEntidadesConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    List<EntidadPrestadora> entidadesAux = new ArrayList<>();
    for(Integer id : idRelacionados) {
      entidadesAux.add(this.entidadesPrestadoras.get(id));
    }
    return entidadesAux;
  }

  private void crearEntidad(CSVRecord csvRecord) {
    Entidad entidad = new Entidad();
    //entidad.setId(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)));
    entidad.setNombre(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    entidad.setDenominacion(new Denominacion(csvRecord.get(PosicionColumnasCSV.DENOMINACION)));
    this.entidades.put(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)), entidad);
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