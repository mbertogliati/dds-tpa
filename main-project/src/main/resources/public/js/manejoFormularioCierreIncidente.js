document.addEventListener("DOMContentLoaded", function() {
    var provinciaSelector = document.getElementById("provincia");
    var departamentoSelector = document.getElementById("departamento");
    var localidadSelector = document.getElementById("localidad");
    var incidenteSelector = document.getElementById("incidente");

    provinciaSelector.addEventListener("change", function(){
        buscarOpciones(provinciaSelector, departamentoSelector,"departamentos");
    });
    departamentoSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidades");
    });
    localidadSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(localidadSelector, incidenteSelector,"incidentes");
    });
    departamentoSelector.addEventListener("change", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidades");
    });
    localidadSelector.addEventListener("change", function(){
        buscarOpciones(localidadSelector, incidenteSelector,"incidentes");
    });

    function buscarOpciones(selector, selectorObjetivo, nombreBuscados){
        var selectorId = selector.value;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/obtener/" + nombreBuscados + "?selectorId=" + selectorId, true);

        xhr.onload = function() {
            if (xhr.status === 200) {
                selectorObjetivo.innerHTML = xhr.responseText;
            } else {
                console.error("Error al obtener " + nombreBuscados + ".");
            }
        };

        xhr.send();
    }

});