export function buscarOpciones(selector, selectorObjetivo, nombreBuscados){
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