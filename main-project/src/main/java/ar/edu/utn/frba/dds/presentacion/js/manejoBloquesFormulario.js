document.addEventListener("DOMContentLoaded", function () {
    const bloquesDinamicos = document.getElementById("bloquesDinamicos");
    let numeroServicio = 1; // Inicializar el número de servicio

    // Función para agregar un nuevo bloque dinámico
    function agregarBloqueDinamico() {
        const plantillaBloque = document.querySelector(".dynamic-block");
        const nuevoBloque = plantillaBloque.cloneNode(true);

        // Actualizar el número de servicio en el encabezado del bloque
        numeroServicio++;
        const tituloServicio = nuevoBloque.querySelector("#titulo-servicio");
        tituloServicio.textContent = `Servicio ${numeroServicio}`;

        bloquesDinamicos.appendChild(nuevoBloque);
    }

    // Función para eliminar el último bloque dinámico
    function eliminarBloqueDinamico() {
        const bloques = bloquesDinamicos.querySelectorAll(".dynamic-block");
        if (bloques.length > 1) {
            bloquesDinamicos.removeChild(bloques[bloques.length - 1]);
            numeroServicio--; // Disminuir el número de servicio cuando se elimina un bloque
        }
    }

    // Agrego escuchadores de eventos a los botones
    const botonAgregarBloque = document.getElementById("add-block");
    const botonEliminarBloque = document.getElementById("remove-block");

    botonAgregarBloque.addEventListener("click", agregarBloqueDinamico);
    botonEliminarBloque.addEventListener("click", eliminarBloqueDinamico);
});
