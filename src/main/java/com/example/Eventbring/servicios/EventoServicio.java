/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.servicios;

import com.example.Eventbring.controladores.UsuarioController;
import com.example.Eventbring.entidades.Asistencia;
import com.example.Eventbring.entidades.Evento;
import com.example.Eventbring.entidades.Usuario;


import com.example.Eventbring.errores.ErrorServicio;
import com.example.Eventbring.repositorios.EventoRepositorio;

import com.example.Eventbring.repositorios.UsuarioRepositorio;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lauta
 */
@Service
public class EventoServicio extends HttpServlet {

    @Autowired
    private EventoRepositorio eventoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private AsistenciaServicio asistenciaServicio;

    

    @Transactional
    public Evento registrar(String nombre, Date fecha_hora, Integer cupo, String direccion, String tipo_evento) throws ErrorServicio {
        validar(nombre, fecha_hora, cupo, direccion, tipo_evento);

        
        Evento evento = new Evento();
              
        evento.setFecha_hora(fecha_hora);
  
        evento.setNombre(nombre);

        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }                
        String userName = userDetails.getUsername();
        
        

        evento.setAnfitrion(userName);

        evento.setCupo(cupo);

        evento.setDireccion(direccion);

        evento.setTipo_evento(tipo_evento);
        
        evento.setAlta(Boolean.FALSE);

        return eventoRepositorio.save(evento);
    }

    @Transactional
    public Evento modificar(String id, String nombre, Date fecha, Integer cupo, String direccion, String tipo_evento) throws ErrorServicio {
        validar(nombre, fecha, cupo, direccion, tipo_evento);

        Evento evento = eventoRepositorio.getById(id);

        evento.setFecha_hora(fecha);
        evento.setNombre(nombre);     

        evento.setCupo(cupo);
        
        evento.setTipo_evento(tipo_evento);

        evento.setDireccion(direccion);

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
    
    
    @Transactional
    public void reducirCupo(String id) throws ErrorServicio {

        Evento evento = eventoRepositorio.getById(id);

        if(evento.getCupo() > 0){
        evento.setCupo(evento.getCupo() - 1);
        }
        
        evento.setAlta(Boolean.TRUE);
       
        eventoRepositorio.save(evento);
    }
    
    
    public List<Evento> listarEventosALosQueAsistire() {
        
        List<Asistencia> asistencias = asistenciaServicio.listarTodos();
        

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        
        String userName = userDetails.getUsername();

        Usuario usuario = usuarioRepositorio.buscarPorUsuario(userName);
        
        List<Evento> eventos = new ArrayList();
        
        for (Asistencia asistencia : asistencias) {
            if(asistencia.getParticipantes().equals(usuario.getId())){
            String id_evento = asistencia.getEventos();
            Evento e = getById(id_evento);           
            eventos.add(e);
            }
        }
        
        return eventos;
    }
    
    

    public void validar(String nombre, Date fecha_hora,  Integer cupo, String direccion, String tipo_evento) throws ErrorServicio {

        if (fecha_hora == null) {
            throw new ErrorServicio("La fecha no puede estar vacia");
        }

        if (nombre.isEmpty() || nombre == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new ErrorServicio("El local no puede estar vacio");
        }

        if (cupo < 0 || cupo == null) {
            throw new ErrorServicio("El cupo no puede estar vacio o ser menor a 0");
        }

        if (tipo_evento.isEmpty() || tipo_evento == null) {
            throw new ErrorServicio("El tipo de evento no puede estar vacio");
        }

    }
}
