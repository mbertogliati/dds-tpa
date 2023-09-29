//CAMBIAR A NUEVO ARCHIVO JAVASCRIPT
function mostrarEsconderFechasDeSemana(){
    var fechasDeSemana = document.getElementById("fechas_de_semana_container");
    if(document.getElementById("momento_notificacion").value === "alMomento"){
        fechasDeSemana.style.display = "none";
        return;
    }
    fechasDeSemana.style.display = "block";
}
document.addEventListener("DOMContentLoaded", function() {

    document.getElementById("momento_notificacion").addEventListener("change",mostrarEsconderFechasDeSemana);

    const selectMedio = document.getElementById("medio_notificacion");
    const medioNotificacion = selectMedio.getAttribute("data-medio");
    const selectMomento = document.getElementById("momento_notificacion");
    const momentoNotificacion = selectMomento.getAttribute("data-momento");

    selectMedio.value = medioNotificacion
    selectMomento.value = momentoNotificacion

    const selectsDias = document.getElementsByClassName("dia");
    for (let i = 0; i < selectsDias.length; i++){
        const selectDia = selectsDias[i];
        selectDia.value = selectDia.getAttribute("data-dia");
    }

    const inputsTime = document.getElementsByClassName("time-notificacion");
    const stepEnMinutos = 30;
    const stepEnSegundos = stepEnMinutos * 60;
    for (let i = 0; i < inputsTime.length; i++){
        inputsTime[i].step = stepEnSegundos.toString();
    }
    mostrarEsconderFechasDeSemana();

    var provinciaSelector = document.getElementById("provincia");
    var departamentoSelector = document.getElementById("departamento");
    var localidadSelector = document.getElementById("localidad");

    provinciaSelector.addEventListener("change", function(){
        buscarOpciones(provinciaSelector, departamentoSelector,"departamentos");
    });
    departamentoSelector.addEventListener("DOMSubtreeModified", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidades");
    });
    departamentoSelector.addEventListener("change", function(){
        buscarOpciones(departamentoSelector, localidadSelector,"localidades");
    });

    function buscarOpciones(selector, selectorObjetivo, nombreBuscados){
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
});