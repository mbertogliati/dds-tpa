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