/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.servicios;

import com.example.Eventbring.entidades.Evento;
import com.example.Eventbring.entidades.Local;
import com.example.Eventbring.entidades.Usuario;
import com.example.Eventbring.errores.ErrorServicio;
import com.example.Eventbring.repositorios.EventoRepositorio;
import com.example.Eventbring.repositorios.LocalRepositorio;
import com.example.Eventbring.repositorios.UsuarioRepositorio;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lauta
 */
@Service
public class EventoServicio {

    @Autowired
    private EventoRepositorio eventoRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private LocalRepositorio localRepositorio;

    @Transactional
    public Evento registrar(Date fecha, String nombre, String nombreUsuario, String direccion) throws ErrorServicio {
        validar(fecha, nombre, nombreUsuario, direccion);

        Evento evento = new Evento();

        evento.setFecha_hora(fecha);
        evento.setNombre(nombre);
    
        Usuario usuario = usuarioRepositorio.buscarPorNombre(nombreUsuario);
        if (usuario != null) {
            evento.setAnfitrion(usuario);
        } else {
            throw new ErrorServicio("No se encontro al usuario");
        }
        
        Local local = localRepositorio.buscarPorDireccion(direccion);
        if (local != null) {
            evento.setLocal(local);
        } else {
            throw new ErrorServicio("No se encontro al local");
        }

        return eventoRepositorio.save(evento);
    }

    @Transactional
    public Evento modificar(String id, Date fecha, String nombre, String nombreUsuario, String direccion) throws ErrorServicio {
        validar(fecha, nombre, nombreUsuario, direccion);

        Evento evento = eventoRepositorio.getById(id);

        evento.setFecha_hora(fecha);
        evento.setNombre(nombre);
    
        Usuario usuario = usuarioRepositorio.buscarPorNombre(nombreUsuario);
        if (usuario != null) {
            evento.setAnfitrion(usuario);
        } else {
            throw new ErrorServicio("No se encontro al usuario");
        }
        
        Local local = localRepositorio.buscarPorDireccion(direccion);
        if (local != null) {
            evento.setLocal(local);
        } else {
            throw new ErrorServicio("No se encontro al local");
        }

        return eventoRepositorio.save(evento);
    }

    @Transactional
    public void borrar(String id) throws ErrorServicio {

        Evento evento = eventoRepositorio.getById(id);

        eventoRepositorio.delete(evento);
    }

    @Transactional(readOnly = true)
    public Evento getById(String id) {
        return eventoRepositorio.getById(id);
    }


    @Transactional(readOnly = true)
    public List<Evento> listarTodos() {
        return eventoRepositorio.findAll();
    }

    public void validar(Date fecha, String nombre, String nombreUsuario, String direccion) throws ErrorServicio {

        if (fecha == null) {
            throw new ErrorServicio("La fecha no puede estar vacia");
        }

        if (nombre.isEmpty() || nombre == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

        
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new ErrorServicio("El usuario no puede estar vacio");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new ErrorServicio("El local no puede estar vacio");
        }

        
    }
}
