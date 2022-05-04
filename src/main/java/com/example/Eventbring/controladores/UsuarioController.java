/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.controladores;

import com.example.Eventbring.entidades.Usuario;
import com.example.Eventbring.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Irina
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    String usuarioregistrado;
            
    @Autowired
    private UsuarioServicio us; 
    
    @GetMapping("/registro")
    public String registrarse() {
        return "registro";
    }
    
    @PostMapping("/registro")
    public String guardarUsuario (@RequestParam String username,@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String clave , @RequestParam String ciudad , @RequestParam Integer telefono  ){
       try {
           Usuario u = us.registrar(username, nombre, apellido, email, clave, ciudad, telefono);
           usuarioregistrado = username;
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
       return "redirect:/login"; 
    }

    public String getUsuarioregistrado() {
        return usuarioregistrado;
    }
    
    
}
