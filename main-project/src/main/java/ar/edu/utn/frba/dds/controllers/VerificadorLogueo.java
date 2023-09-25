package ar.edu.utn.frba.dds.controllers;

public class VerificadorLogueo {
  public static Boolean noEstaLogueado(Object objeto){
    return objeto == null;
  }
}
