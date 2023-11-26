import {buscarOpciones} from "../noAuth/buscarOpciones.js";

document.addEventListener("DOMContentLoaded", function() {
    var provinciaSelector = document.getElementById("provincia");
    var departamentoSelector = document.getElementById("departamento");
    var localidadSelector = document.getElementById("localidad");
    var entidadSelector = document.getElementById("entidad");
    var establecimientoSelector = document.getElementById("establecimiento");
    var listadoServicios = document.getElementById("listaServicios");

    provinciaSelector.addEventListener("change", function(){
        buscarOpciones(provinciaSelector, departamentoSelector,"departamentosIncidentes");
    });
    departamentoSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidadesIncidentes");
    });
    localidadSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(localidadSelector, entidadSelector,"entidadesIncidentes");
    });
    entidadSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(entidadSelector, establecimientoSelector,"establecimientosIncidentes");
    });
    establecimientoSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(establecimientoSelector, listadoServicios,"serviciosPrestadosIncidentes");
    });
    departamentoSelector.addEventListener("change", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidadesIncidentes");
    });
    localidadSelector.addEventListener("change", function(){
        buscarOpciones(localidadSelector, entidadSelector,"entidadesIncidentes");
    });
    entidadSelector.addEventListener("change", function(){
        buscarOpciones(entidadSelector, establecimientoSelector,"establecimientosIncidentes");
    });
    establecimientoSelector.addEventListener("change", function(){
        buscarOpciones(establecimientoSelector, listadoServicios,"serviciosPrestadosIncidentes");
    });
});