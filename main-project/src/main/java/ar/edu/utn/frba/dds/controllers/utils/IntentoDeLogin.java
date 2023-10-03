package ar.edu.utn.frba.dds.controllers.utils;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class IntentoDeLogin {
    private LocalDateTime ultimoIntento;
    private Long cantIntentosFallidos;

    private Long segundosDeEspera;
    public IntentoDeLogin(){
        this.reset();
    }
    public boolean puedeLoguearse(){
        return ! LocalDateTime.now().isBefore(ultimoIntento.plusSeconds(segundosDeEspera));
    }
    public void sumarIntento(){
        cantIntentosFallidos++;
        ultimoIntento = LocalDateTime.now();
        segundosDeEspera = calcularSegundosDeEspera();
    }
    public String getSegundosRestantes(){
        Long segundosRestantes = Math.max(segundosDeEspera - ChronoUnit.SECONDS.between(ultimoIntento,LocalDateTime.now()),0);
        return segundosRestantes.toString();
    }

    public void reset(){
        cantIntentosFallidos = 0L;
        ultimoIntento = LocalDateTime.now();
        segundosDeEspera = 0L;
    }

    private Long calcularSegundosDeEspera(){
        if(cantIntentosFallidos <= 3){
            return 0L;
        }
        if(cantIntentosFallidos <= 5){
            return 60L;
        }

        return 300L;
    }
}
