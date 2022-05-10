/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.controladores;

import com.example.Eventbring.entidades.Asistencia;
import com.example.Eventbring.entidades.Evento;
import com.example.Eventbring.entidades.Usuario;
import com.example.Eventbring.repositorios.EventoRepositorio;
import com.example.Eventbring.servicios.AsistenciaServicio;
import com.example.Eventbring.servicios.EventoServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lauta
 */
@Controller
@RequestMapping("/listaeventos")
public class ListController {
    
    @Autowired
    private AsistenciaServicio as;
    
    @Autowired
    private EventoServicio es;
    
    @Autowired
    private EventoRepositorio er;
    
    @GetMapping("/asistir/{id}")
    public String asistir(@PathVariable String id, HttpSession session) {

        try {
            
            Usuario u = (Usuario) session.getAttribute("usuariosession");
                       
            as.registrar(u.getId(), id);
            

            es.reducirCupo(id);
            
            
            
            return "redirect:/listaeventos";
        } catch (Exception e) {
            return "redirect:/listaeventos";
        }

    }
    
    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable String id) {

        try {
            
            List<Asistencia> asistencias = as.listarTodos();
            
            for (Asistencia asistencia : asistencias) {
                if(asistencia.getEventos().equals(id)){
                    as.borrar(asistencia.getId());
                }
                
            }
            
            es.borrar(id);
            
            return "redirect:/listaeventos";
        } catch (Exception e) {
            return "redirect:/listaeventos";
        }

    }
    
}
