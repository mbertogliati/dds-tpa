package ar.edu.utn.frba.dds.modelos.importadorEntidades;

import ar.edu.utn.frba.dds.modelos.entidades.Denominacion;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
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
    DIRECCION,
    ENTIDADES_RELACIONADAS
  }

  private static final class TipoRecord {
    public static final String Establecimiento = "Establecimiento";
    public static final String Entidad = "Entidad";
    public static final String Control = "Control";
    public static final String Prestador = "Prestador";
  }

  private Map<Integer, Establecimiento> establecimientos = new HashMap<>();
  private Map<Integer, Entidad> entidades = new HashMap<>();
  private Map<Integer, EntidadPrestadora> entidadesPrestadoras = new HashMap<>();
  List<OrganismoControl> organismosDeControl = new ArrayList<OrganismoControl>();

  public List<OrganismoControl> importar(String path) {
    try (
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
    ) {
      List<CSVRecord> recordsSinEncabezado = csvParser.stream().filter(csvRecord -> csvRecord.getRecordNumber() > 0).toList();
      Stream<CSVRecord> streamEstablecimientos = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Establecimiento));
      Stream<CSVRecord> streamEntidades = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Entidad));
      Stream<CSVRecord> streamEntidadesPrestadoras = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Prestador));
      Stream<CSVRecord> streamOrganismosDeControl = recordsSinEncabezado.stream().filter(csvRecord -> csvRecord.get(PosicionColumnasCSV.TIPO).equals(TipoRecord.Control));

      streamEstablecimientos.forEach(this::crearEstablecimiento);

      streamEntidades.forEach(this::crearEntidad);

      streamEntidadesPrestadoras.forEach(this::crearEntidadPrestadora);

      streamOrganismosDeControl.forEach(this::crearOrganismosControl);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return organismosDeControl;
  }

  private void crearEstablecimiento(CSVRecord csvRecord) {
    Establecimiento establecimiento = new Establecimiento();
    establecimiento.setNombre(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    establecimiento.setDenominacion(new Denominacion(csvRecord.get(PosicionColumnasCSV.DENOMINACION)));

    this.establecimientos.put(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)), establecimiento);
  }

  private void crearEntidad(CSVRecord csvRecord) {
    List<Establecimiento> establecimientosRelacionados = this.obtenerEstablecimientosRelacionados(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS));

    Entidad entidad = new Entidad();
    entidad.setNombre(csvRecord.get(PosicionColumnasCSV.NOMBRE));
    entidad.setDenominacion(new Denominacion(csvRecord.get(PosicionColumnasCSV.DENOMINACION)));
    entidad.setEstablecimientos(establecimientosRelacionados);

    establecimientosRelacionados.forEach(e -> e.setEntidad(entidad));

    this.entidades.put(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)), entidad);
  }
  private List<Establecimiento> obtenerEstablecimientosRelacionados(String idEstablecimientosConcatenadas) {
    List<Integer> idRelacionados = Arrays.stream(idEstablecimientosConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    List<Establecimiento> establecimientosAux = new ArrayList<>();

    for(Integer id : idRelacionados) {
      establecimientosAux.add(this.establecimientos.get(id));
    }

    return establecimientosAux;
  }

  private void crearEntidadPrestadora(CSVRecord csvRecord) {
    EntidadPrestadora entidadPrestadora = new EntidadPrestadora(csvRecord.get(PosicionColumnasCSV.NOMBRE));

    List<Entidad> entidadesRelacionadas = obtenerEntidadesRelacionadas(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS));
    entidadPrestadora.setEntidades(entidadesRelacionadas);

    entidadesRelacionadas.forEach(e -> e.setPrestadora(entidadPrestadora));

    entidadesPrestadoras.put(Integer.parseInt(csvRecord.get(PosicionColumnasCSV.ID)), entidadPrestadora);
  }
  private List<Entidad> obtenerEntidadesRelacionadas(String idEntidadesConcatenadas) {
    List<Integer> idRelacionados = Arrays.stream(idEntidadesConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    List<Entidad> entidadesAux = new ArrayList<>();

    for(Integer id : idRelacionados) {
      entidadesAux.add(this.entidades.get(id));
    }

    return entidadesAux;
  }

  private void crearOrganismosControl(CSVRecord csvRecord) {
    OrganismoControl organismoControl = new OrganismoControl(csvRecord.get(PosicionColumnasCSV.NOMBRE));

    List<EntidadPrestadora> entidadesPrestadorasRelacionadas = obtenerEntidadesPrestadorasRelacionadas(csvRecord.get(PosicionColumnasCSV.ENTIDADES_RELACIONADAS));
    organismoControl.getEntidadesPrestadoras().addAll(entidadesPrestadorasRelacionadas);

    entidadesPrestadorasRelacionadas.forEach(ep -> ep.setOrganismoControl(organismoControl));

    this.organismosDeControl.add(organismoControl);
  }
  private List<EntidadPrestadora> obtenerEntidadesPrestadorasRelacionadas(String idEntidadesConcatenadas) {
    List<Integer> idRelacionados = Arrays.stream(idEntidadesConcatenadas.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    List<EntidadPrestadora> entidadesAux = new ArrayList<>();
    for(Integer id : idRelacionados) {
      entidadesAux.add(this.entidadesPrestadoras.get(id));
    }
    return entidadesAux;
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