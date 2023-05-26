package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.domain.entidades.Denominacion;
import ar.edu.utn.frba.dds.domain.entidades.OrganismoControl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;



public class ImportarEntidadCSV implements ImportarEntidadAdapter{

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
    Entidad entidad = null;
    Denominacion denominacion = new Denominacion(csvRecord.get(2));

    switch (csvRecord.get(0)){
      case "0":
        entidad = new EntidadPrestadora(csvRecord.get(1), denominacion);
        break;
      case "1":
        entidad = new OrganismoControl(csvRecord.get(1), denominacion);
        break;
      default:
        throw new RuntimeException("Tipo de entidad no valido");
    }
    return entidad;
  }
}
/*
* EJEMPLO .CSV
*
* TipoEntidad(0,1);Nombre;Denominacion
* 0;Linea B;Transporte;Juan;Perez;juan_perez@gmail.com
* 0;Linea 60;Transporte;Rodrigo;Pena;rpena@hotmail.com
* 1;Ministerio De Transporte;Administracion Estatal;Juan;Perez;juan_perez@gmail.com
* 1;Banco Nacion;Banco;Rodrigo;Pena;rpena@hotmail.com
*
* */
