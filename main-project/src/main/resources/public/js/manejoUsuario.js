//CAMBIAR A NUEVO ARCHIVO JAVASCRIPT
function mostrarEsconderFechasDeSemana(){
    var fechasDeSemana = document.getElementById("fechas_de_semana");
    if(this.getAttribute("value") == "sinApuro"){
        fechasDeSemana.style.visibility = "visible";
        return;
    }
    fechasDeSemana.style.visibility = "hidden";
}
function agregarEvento(){

}

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("momento_notificacion").addEventListener("change",mostrarEsconderFechasDeSemana);

    const selectMedio = document.getElementById("medio_notificacion");
    const medioNotificacion = selectMedio.getAttribute("data-medio");
    const selectMomento = document.getElementById("momento_notificacion");
    const momentoNotificacion = selectMomento.getAttribute("data-momento");

    for (let i = 0; i < selectMedio.options.length; i++) {
        if (selectMedio.options[i].value === medioNotificacion) {
            selectMedio.options[i].selected = true;
            break;
        }
    }

    for (let i = 0; i < selectMomento.options.length; i++) {
        if (selectMomento.options[i].value === momentoNotificacion) {
            selectMomento.options[i].selected = true;
            break;
        }
    }

    const selectsDias = document.getElementsByClassName("dia");
    for (let i = 0; i < selectsDias.length; i++){
        const selectDia = selectsDias[i];
        const valorDia = selectDia.getAttribute("data-dia");
        for (let j = 0; j < selectDia.options.length; j++) {
            if (selectDia.options[j].value === valorDia) {
                selectDia.options[j].selected = true;
                break;
            }
        }
    }

    const inputsTime = document.getElementsByClassName("time-notificacion");
    const stepEnMinutos = 30;
    const stepEnSegundos = stepEnMinutos * 60;
    for (let i = 0; i < inputsTime.length; i++){
        inputsTime[i].step = stepEnSegundos.toString();
    }
});