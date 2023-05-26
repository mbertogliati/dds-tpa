package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class ImportarEntidadCSV implements ImportarEntidadAdapter{
  @Override
  public List<Entidad> crearEntidades(String path) {
    //TODO: IMPLEMENTAR IMPORTAR ENTIDADES POR CSV
    return new ArrayList<Entidad>();
  }
}

/*
* public class CSVImporter {
    public static void main(String[] args) {
        try {
            Reader reader = new FileReader("ruta/al/archivo.csv");
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {
                String column1 = csvRecord.get(0);
                String column2 = csvRecord.get(1);
                // Aquí puedes acceder a cada columna del registro y realizar las operaciones necesarias
                System.out.println("Columna 1: " + column1);
                System.out.println("Columna 2: " + column2);
            }

            csvParser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
* EJEMPLO .CSV
*
* Nombre;Apellido;Apodo
* joaquin;gomez;joaco
* juan;gomez;juancho
* juan;gomez;juancho
* juan;gomez;juancho
*
* */
