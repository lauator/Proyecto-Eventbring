/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.servicios;

import com.example.Eventbring.entidades.Usuario;
import com.example.Eventbring.enums.Role;
import com.example.Eventbring.errores.ErrorServicio;
import com.example.Eventbring.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Irina
 */
@Service
public class UsuarioServicio implements UserDetailsService {
      @Autowired
    private UsuarioRepositorio usuarioRepositorio; 
    
    @Transactional
    public Usuario registrar(String username, String nombre, String apellido, String email, String clave, String ciudad, Integer telefono) throws ErrorServicio {
        validar(username, nombre, apellido, email, clave, ciudad, telefono);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setUsername(username);
        usuario.setApellido(apellido);
        usuario.setMail(email);
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();     
        usuario.setClave(encoder.encode(clave));
        
        
        usuario.setCiudad(ciudad);
        usuario.setTelefono(telefono);
        
        usuario.setRole(Role.USER);

        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario modificar(String id, String username, String nombre, String apellido, String email, String clave, String ciudad, Integer telefono) throws ErrorServicio {
        validar(username, nombre, apellido, email, clave, ciudad, telefono);

        Usuario usuario = usuarioRepositorio.getById(id);
        
        if (usuario==null) {
            throw new ErrorServicio("No existe un usuario con esa ID");
        }

        usuario.setNombre(nombre);
        usuario.setUsername(username);
        usuario.setApellido(apellido);
        usuario.setMail(email);
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        usuario.setClave(encoder.encode(clave));
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
    
    

    public void validar(String username, String nombre, String apellido, String email, String clave, String ciudad, Integer telefono) throws ErrorServicio {
        if (username.isEmpty() || username == null) {
            throw new ErrorServicio("El usuario no puede estar vacio");
        }
        
        if (usuarioRepositorio.buscarPorUsuario(username)!= null) {
            throw new ErrorServicio("Ya existe un usuario con ese nombre");
        }
        
        if (nombre.isEmpty() || nombre == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        
        if (apellido.isEmpty() || apellido == null) {
            throw new ErrorServicio("El apellido no puede estar vacio");
        }
        
        if (email.isEmpty() || email == null) {
            throw new ErrorServicio("El mail no puede estar vacio");
        }
        
        if (usuarioRepositorio.buscarPorEmail(email)!= null) {
            throw new ErrorServicio("Ya existe un usuario con ese email");
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorUsuario(username);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRole());
            permisos.add(p1);

            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getUsername(), usuario.getClave(), permisos);
            return user;

        } else {
            return null;
        }
    }
}
