package ar.edu.utn.frba.dds.controllers.generales.incidentes;

import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ObtenedorListadoIncidentes {
    public static String url = "https://localhost:8080/incidentes";

    public static URI obtenerURI() {
        String envUrl = System.getenv("LISTADO_INCIDENTES_URL");
        if(envUrl != null && !envUrl.isEmpty()) url = envUrl;
        return URI.create(url);
    }

    public static String obtenerHTMLListadoIncidentes(Map<String,Object> model) throws URISyntaxException, IOException, InterruptedException {
        MensajeVista mensajeVista = (MensajeVista) model.get("msg");
        Usuario usuario = (Usuario) model.get("userActual");
        var values = new HashMap<String, String>() {{
            put("tipo", mensajeVista.getTipo());
            put("msg", mensajeVista.getTexto());
            put("idUsuario",String.valueOf(usuario.getId()));
        }};
        //TODO: AGREGAR QUE SOLO TRAIGA INCIDENTES DE LA COMUNIDAD QUE LE MANDEMOS (SEGUN EL ROL SELECCIONADO)

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(obtenerURI())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
