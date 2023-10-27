function obtenerUbicacion() {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var latitud = position.coords.latitude;
            var longitud = position.coords.longitude;
            document.getElementById("latitud").value = latitud;
            document.getElementById("longitud").value = longitud;
        });
    } else {
        alert("La geolocalización no está disponible en este navegador.");
    }
}
function validarFormulario() {
    var campo1 = document.getElementById('latitud').value;
    var campo2 = document.getElementById('longitud').value;

    if (campo1 === '' && campo2 === '') {
        alert('Debes compartir tu ubicación con nosotros para poder registrarte.');
        return false;
    }

    return true;
}