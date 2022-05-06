/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.servicios;


import com.example.Eventbring.entidades.Asistencia;
import com.example.Eventbring.errores.ErrorServicio;
import com.example.Eventbring.repositorios.AsistenciaRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lauta
 */
@Service
public class AsistenciaServicio {
    
    @Autowired
    private AsistenciaRepositorio asistenciaRepo;
    
    public Asistencia registrar(String participantes, String eventos) throws ErrorServicio{
        Asistencia a = new Asistencia();
        
        a.setParticipantes(participantes);
        a.setEventos(eventos);
        
        return asistenciaRepo.save(a);
    
    }
    
    @Transactional(readOnly = true)
    public List<Asistencia> listarTodos() {
        return asistenciaRepo.findAll();
    }
    
}
