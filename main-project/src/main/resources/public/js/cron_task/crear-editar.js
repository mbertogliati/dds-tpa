function mostrarSeccion(){

    var tipoSeleccionado = document.getElementById("tipo").value;
    var seccionesCondicionales = document.getElementsByClassName("seccion-condicional");

    Array.prototype.forEach.call(seccionesCondicionales,(element) => {
        element.style.display = "none";
        if(element.getAttribute("dependsOnValue") == tipoSeleccionado)
            element.style.display = "block";
    });
}

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("tipo").addEventListener("change", mostrarSeccion);
    mostrarSeccion();
});