// Function to load a template and insert it into a specified element
function cargarTemplate(nombreTemplate, idElemento) {
    fetch(nombreTemplate)
        .then(response => response.text())
        .then(data => {
            document.getElementById(idElemento).innerHTML = data;
        });
}

function resaltarElementoActivo(idElementoActivo) {
    // Remove "active" class from all nav items
    const elementosNav = document.querySelectorAll('.nav-item');
    elementosNav.forEach(item => item.classList.remove('active'));

    // Add "active" class to the specified navigation item
    const elementoActivo = document.getElementById(idElementoActivo);
    if (elementoActivo != null) {
        elementoActivo.classList.add('active');
    } else {
    }
}
