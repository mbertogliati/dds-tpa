document.addEventListener("DOMContentLoaded", function () {
    const bloquesDinamicos = document.getElementById("bloquesDinamicos");

    // Función para agregar un nuevo campo de servicio
    function agregarCampoDeServicio() {
        const plantillaCampo = document.querySelector(".dynamic-block");
        const nuevoCampo = plantillaCampo.cloneNode(true);
        nuevoCampo.value = "";

        bloquesDinamicos.appendChild(nuevoCampo);
    }

    // Función para eliminar el último campo de servicio
    function eliminarCampoDeServicio() {
        const campos = bloquesDinamicos.querySelectorAll(".dynamic-block");
        if (campos.length > 1) {
            bloquesDinamicos.removeChild(campos[campos.length - 1]);
        }
    }

    // Agrego escuchadores de eventos a los botones
    const botonAgregarCampo = document.getElementById("add-service");
    const botonEliminarCampo = document.getElementById("remove-service");

    botonAgregarCampo.addEventListener("click", agregarCampoDeServicio);
    botonEliminarCampo.addEventListener("click", eliminarCampoDeServicio);
});