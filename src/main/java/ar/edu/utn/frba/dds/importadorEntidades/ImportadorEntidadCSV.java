package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Denominacion;
import ar.edu.utn.frba.dds.domain.entidades.ControlEntidades;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;



public class ImportadorEntidadCSV implements ImportadorEntidadAdapter {

  public List<Entidad> crearEntidades(String path){
    List<Entidad> entidades = new ArrayList<Entidad>();
    try (
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
    ) {
      for (CSVRecord csvRecord : csvParser) {
        entidades.add(crearEntidad(csvRecord));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return entidades;
  }

  private Entidad crearEntidad(CSVRecord csvRecord){
    Denominacion denominacion = new Denominacion(csvRecord.get(1));
    Entidad entidad = new Entidad(csvRecord.get(0), denominacion);

    return entidad;
  }
}
/*
* EJEMPLO .CSV
*
* Nombre;Denominacion
* Linea B;Transporte;Juan;Perez;juan_perez@gmail.com
* Linea 60;Transporte;Rodrigo;Pena;rpena@hotmail.com
* Ministerio De Transporte;Administracion Estatal;Juan;Perez;juan_perez@gmail.com
* Banco Nacion;Banco;Rodrigo;Pena;rpena@hotmail.com
*
* */
