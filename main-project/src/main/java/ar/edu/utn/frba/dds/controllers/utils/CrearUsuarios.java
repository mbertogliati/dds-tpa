package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;

public class CrearUsuarios {
  public static void main(String[] args) {
    UsuarioRepositorio repo = new UsuarioRepositorio(new CreadorEntityManager().entityManagerCreado());

    EstrategiaHash hasheador = new HashPBKDF2();

    List<Usuario> listaUsuarios = new ArrayList<>();

    Usuario usuario1 = new Usuario();
    usuario1.setPassword(hasheador.hashear("password1"));
    usuario1.setUsername("usuario1");
    listaUsuarios.add(usuario1);

    Usuario usuario2 = new Usuario();
    usuario2.setPassword(hasheador.hashear("password2"));
    usuario2.setUsername("usuario2");
    listaUsuarios.add(usuario2);

    Usuario usuario3 = new Usuario();
    usuario3.setPassword(hasheador.hashear("password3"));
    usuario3.setUsername("usuario3");
    listaUsuarios.add(usuario3);

    Usuario usuario4 = new Usuario();
    usuario4.setPassword(hasheador.hashear("password4"));
    usuario4.setUsername("usuario4");
    listaUsuarios.add(usuario4);

    Usuario usuario5 = new Usuario();
    usuario5.setPassword(hasheador.hashear("password5"));
    usuario5.setUsername("usuario5");
    listaUsuarios.add(usuario5);

    Usuario usuario6 = new Usuario();
    usuario6.setPassword(hasheador.hashear("password6"));
    usuario6.setUsername("usuario6");
    listaUsuarios.add(usuario6);

    Usuario usuario7 = new Usuario();
    usuario7.setPassword(hasheador.hashear("password7"));
    usuario7.setUsername("usuario7");
    listaUsuarios.add(usuario7);

    Usuario usuario8 = new Usuario();
    usuario8.setPassword(hasheador.hashear("password8"));
    usuario8.setUsername("usuario8");
    listaUsuarios.add(usuario8);

    Usuario usuario9 = new Usuario();
    usuario9.setPassword(hasheador.hashear("password9"));
    usuario9.setUsername("usuario9");
    listaUsuarios.add(usuario9);

    Usuario usuario10 = new Usuario();
    usuario10.setPassword(hasheador.hashear("password10"));
    usuario10.setUsername("usuario10");
    listaUsuarios.add(usuario10);

    listaUsuarios.forEach(u -> repo.guardar(u));
  }
}
