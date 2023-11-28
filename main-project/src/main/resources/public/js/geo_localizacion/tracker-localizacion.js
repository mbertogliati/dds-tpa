var opcionesGeoLocalizacion = {
  enableHighAccuracy: false, //true: el dispositivo intentará tener mucha precisión (consume más energía).
  timeout: 10000, // devolver posición cada X ms
  maximumAge: 0, // 0 = no se usa una posición cacheada
};

var trackerGeoLocalizacionId; //identificador del tracker de localizacion

function enviarLocalizacion(position) {
    console.log("Enviando localización al servidor!");
    console.log(JSON.stringify(position));
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/usuario-localizacion");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify({
        latitud: position.coords.latitude,
        longitud: position.coords.longitude
    }));
}

function iniciarTracker() {
    console.log("Intentando obtener geo localizacion!");

    if ("geolocation" in navigator) {
        if(typeof(trackerGeoLocalizacionId) == "undefined" ) {
            trackerGeoLocalizacionId = navigator.geolocation.watchPosition(
                (position) => {
                    console.log("Localizacion obtenida!");
                    console.log(position);
                    enviarLocalizacion(position);
                },
                (error) => {
                    console.log("Ocurrió un error al obtener la geolocalización!");
                    console.log(error);
                },
                opcionesGeoLocalizacion
            );
            console.log("tracker de geolocalizacion iniciado con éxito");
            console.log(trackerGeoLocalizacionId);
        }
    } else {
        console.log("[INFO]: La geolocalización no está disponible en este navegador.");
    }
}

document.addEventListener("DOMContentLoaded", function () {
    iniciarTracker();
});