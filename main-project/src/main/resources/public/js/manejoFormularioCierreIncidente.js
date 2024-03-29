import {buscarOpciones} from "../noAuth/buscarOpciones.js";

document.addEventListener("DOMContentLoaded", function() {
    var provinciaSelector = document.getElementById("provincia");
    var departamentoSelector = document.getElementById("departamento");
    var localidadSelector = document.getElementById("localidad");
    var incidenteSelector = document.getElementById("incidente");

    provinciaSelector.addEventListener("change", function(){
        buscarOpciones(provinciaSelector, departamentoSelector,"departamentosCierreIncidentes");
    });
    departamentoSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidadesCierreIncidentes");
    });
    localidadSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(localidadSelector, incidenteSelector,"incidentesCierre");
    });
    departamentoSelector.addEventListener("change", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidadesCierreIncidentes");
    });
    localidadSelector.addEventListener("change", function(){
        buscarOpciones(localidadSelector, incidenteSelector,"incidentesCierre");
    });

});