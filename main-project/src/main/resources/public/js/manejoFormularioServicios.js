document.addEventListener("DOMContentLoaded", function() {
    var tipoEtiquetaSelectors = document.querySelectorAll(".tipo-etiqueta");
    var valorEtiquetaSelectors = document.querySelectorAll(".valor-etiqueta");

    function buscarEtiquetas(tipoSelector, valorSelector) {
        var tipoId = tipoSelector.value;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/obtener/etiquetas?tipoId=" + tipoId, true);

        xhr.onload = function() {
            if (xhr.status === 200) {
                valorSelector.innerHTML = xhr.responseText;
            } else {
                console.error("Error al obtener etiquetas.");
            }
        };

        xhr.send();
    }

    function agregarEventoSelector(selector, evento) {
        selector.addEventListener(evento, function() {
            var tipoSelector = selector.parentElement.querySelector(".tipo-etiqueta");
            var valorSelector = selector.parentElement.querySelector(".valor-etiqueta");
            buscarEtiquetas(tipoSelector, valorSelector);
        });
    }

    tipoEtiquetaSelectors.forEach(function(selector) {
        agregarEventoSelector(selector, "change");
    });

    // Por ejemplo, para agregar:
    document.getElementById("add-service").addEventListener("click", function(event) {
        event.preventDefault();

        var nuevoBloque = document.querySelector(".dynamic-block").cloneNode(true);
        document.getElementById("bloquesDinamicos").appendChild(nuevoBloque);

        var nuevoTipoSelector = nuevoBloque.querySelector(".tipo-etiqueta");
        var nuevoValorSelector = nuevoBloque.querySelector(".valor-etiqueta");

        agregarEventoSelector(nuevoTipoSelector, "change");
        // Puedes agregar más eventos según sea necesario.
    });

    // Para eliminar:
    document.getElementById("remove-service").addEventListener("click", function(event) {
        event.preventDefault();

        var bloques = document.querySelectorAll(".dynamic-block");
        if (bloques.length > 1) {
            bloques[bloques.length - 1].remove();
        }
    });
});