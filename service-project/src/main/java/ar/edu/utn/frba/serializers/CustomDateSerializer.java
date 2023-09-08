package ar.edu.utn.frba.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateSerializer extends StdSerializer<LocalDate> {

  private static final long serialVersionUID = 1L;

  public CustomDateSerializer(){
    super(LocalDate.class);
  }

  @Override
  public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
    gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }
}