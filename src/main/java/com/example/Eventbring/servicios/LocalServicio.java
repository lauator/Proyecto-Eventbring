/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.servicios;

import com.example.Eventbring.entidades.Local;
import com.example.Eventbring.errores.ErrorServicio;
import com.example.Eventbring.repositorios.LocalRepositorio;
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
public class LocalServicio {
    @Autowired
    private LocalRepositorio localRepositorio; 
    
    @Transactional
    public Local registrar(String direccion, Integer telefono, Integer cupo) throws ErrorServicio {
        validar(direccion, telefono, cupo);

        Local local = new Local();

        local.setDireccion(direccion);
        local.setTelefono(telefono);
        local.setCupo(cupo);
        
        

        return localRepositorio.save(local);
    }

    @Transactional
    public Local modificar(String id, String direccion, Integer telefono, Integer cupo) throws ErrorServicio {
        validar(direccion, telefono, cupo);


        Local local = localRepositorio.getById(id);

        local.setDireccion(direccion);
        local.setTelefono(telefono);
        local.setCupo(cupo);
        
        

        return localRepositorio.save(local);
    }

    @Transactional
    public void borrar(String id) throws ErrorServicio {
        
        Local local = localRepositorio.getById(id);

        
        localRepositorio.delete(local);
    }
    
    @Transactional(readOnly = true)
    public Local getById(String id) {
        return localRepositorio.getById(id);
    }

      
    @Transactional(readOnly = true)
    public List<Local> listarTodos() {
        return localRepositorio.findAll();
    }

    public void validar(String direccion, Integer telefono, Integer cupo) throws ErrorServicio {
        if (direccion.isEmpty() || direccion == null) {
            throw new ErrorServicio("La direccion no puede estar vacia");
        }
        
        if ( telefono == null) {
            throw new ErrorServicio("El telefono no puede estar vacio");
        }
        
        if(telefono < 0){
        throw new ErrorServicio("El telefono no puede ser menor a 0");
        }
        
        if ( cupo == null) {
            throw new ErrorServicio("El cupo no puede estar vacio");
        }
        
        if (cupo < 0) {
            throw new ErrorServicio("El cupo no puede ser menor a 0");
        }
        
        
        
        
    }
}
