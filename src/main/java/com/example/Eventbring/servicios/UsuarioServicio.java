/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.servicios;

import com.example.Eventbring.entidades.Usuario;
import com.example.Eventbring.errores.ErrorServicio;
import com.example.Eventbring.repositorios.UsuarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Irina
 */
@Service
public class UsuarioServicio {
      @Autowired
    private UsuarioRepositorio usuarioRepositorio; 
    
    @Transactional
    public Usuario registrar(String nombreUsuario, String nombre, String apellido, String mail, String clave, String ciudad, Integer telefono) throws ErrorServicio {
        validar(nombreUsuario, nombre, apellido, mail, clave, ciudad, telefono);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setNombreusuario(nombreUsuario);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setClave(clave);
        usuario.setCiudad(ciudad);
        usuario.setTelefono(telefono);

        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario modificar(String id, String nombreUsuario, String nombre, String apellido, String mail, String clave, String ciudad, Integer telefono) throws ErrorServicio {
        validar(nombreUsuario, nombre, apellido, mail, clave, ciudad, telefono);

        Usuario usuario = usuarioRepositorio.getById(id);

        usuario.setNombre(nombre);
        usuario.setNombreusuario(nombreUsuario);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setClave(clave);
        usuario.setCiudad(ciudad);
        usuario.setTelefono(telefono);

        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void borrar(String id) throws ErrorServicio {
        
        Usuario usuario = usuarioRepositorio.getById(id);

        
        usuarioRepositorio.delete(usuario);
    }
    
    @Transactional(readOnly = true)
    public Usuario getById(String id) {
        return usuarioRepositorio.getById(id);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorNombre(String nombre) {
        return usuarioRepositorio.buscarPorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepositorio.findAll();
    }

    public void validar(String nombreUsuario, String nombre, String apellido, String mail, String clave, String ciudad, Integer telefono) throws ErrorServicio {
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new ErrorServicio("El usuario no puede estar vacio");
        }
        
        if (nombre.isEmpty() || nombre == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        
        if (apellido.isEmpty() || apellido == null) {
            throw new ErrorServicio("El apellido no puede estar vacio");
        }
        
        if (mail.isEmpty() || mail == null) {
            throw new ErrorServicio("El mail no puede estar vacio");
        }
        
        if (clave.isEmpty() || clave == null) {
            throw new ErrorServicio("La clave no puede estar vacia");
        }
        
        if (ciudad.isEmpty() || ciudad == null) {
            throw new ErrorServicio("La ciudad no puede estar vacia");
        }
        
        if (telefono == null) {
            throw new ErrorServicio("El telefono no puede estar vacio");
        }
    }  
}
