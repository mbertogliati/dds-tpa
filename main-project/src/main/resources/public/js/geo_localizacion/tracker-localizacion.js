var opcionesGeoLocalizacion = {
    enableHighAccuracy: false,
    timeout: 120000,
    maximumAge: 0,
};

var trackerGeoLocalizacionId;

function enviarLocalizacion(position) {
    console.log("Enviando localización al servidor!");
    console.log(JSON.stringify(position));
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/usuario-localizacion");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(
        JSON.stringify({
            latitud: position.coords.latitude,
            longitud: position.coords.longitude,
        })
    );

    // Detener el seguimiento después de enviar la localización
    detenerTracker();
}

function iniciarTracker() {
    console.log("Intentando obtener geo localizacion!");

    if ("geolocation" in navigator) {
        if (typeof trackerGeoLocalizacionId === "undefined") {
            trackerGeoLocalizacionId = navigator.geolocation.watchPosition(
                (position) => {
                    console.log("Localizacion obtenida!");
                    console.log(position);
                    //enviarLocalizacion(position);
                },
                (error) => {
                    console.log("Ocurrió un error al obtener la geolocalización!");
                    console.log(error);
                },
                opcionesGeoLocalizacion
            );
            console.log("tracker de geolocalizacion iniciado con éxito");
            console.log(trackerGeoLocalizacionId);

            // Iniciar un temporizador para reiniciar el seguimiento después de un minuto
            setTimeout(() => {
                iniciarTracker();
            }, 120000);
        }
    } else {
        console.log("[INFO]: La geolocalización no está disponible en este navegador.");
    }
}

function detenerTracker() {
    if (typeof trackerGeoLocalizacionId !== "undefined") {
        navigator.geolocation.clearWatch(trackerGeoLocalizacionId);
        console.log("tracker de geolocalizacion detenido con éxito");
        trackerGeoLocalizacionId = undefined;
    }
}

document.addEventListener("DOMContentLoaded", function () {
    iniciarTracker();
});
