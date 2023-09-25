document.addEventListener("DOMContentLoaded", function() {
    var provinciaSelector = document.getElementById("provincia");
    var departamentoSelector = document.getElementById("departamento");
    var localidadSelector = document.getElementById("localidad");
    var entidadSelector = document.getElementById("entidad");
    var establecimientoSelector = document.getElementById("establecimiento");
    var listadoServicios = document.getElementById("listaServicios");

    provinciaSelector.addEventListener("change", function(){
        buscarOpciones(provinciaSelector, departamentoSelector,"Departamentos");
    });
    departamentoSelector.addEventListener("change", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"Localidades");
    });
    localidadSelector.addEventListener("change", function(){
        buscarOpciones(localidadSelector, entidadSelector,"Entidades");
    });
    entidadSelector.addEventListener("change", function(){
        buscarOpciones(entidadSelector, establecimientoSelector,"Establecimientos");
    });
    establecimientoSelector.addEventListener("change", function(){
        buscarOpciones(establecimientoSelector, listadoServicios,"Servicios");
    });

    function buscarOpciones(selector, selectorObjetivo, nombreBuscados){
        var selectorId = selector.value;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/obtener" + nombreBuscados + "?selectorId=" + selectorId, true);

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