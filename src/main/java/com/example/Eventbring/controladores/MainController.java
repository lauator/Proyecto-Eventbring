/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.controladores;

import com.example.Eventbring.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Irina
 */
@Controller
@RequestMapping("")
public class MainController {

     @Autowired
    private UsuarioServicio us;

    
    @GetMapping("/")
    public String index(@RequestParam(required = false)String login, ModelMap model) {
        if (login!=null) {
            model.put("exito","Logueado con exito");
        }
        
        return "proyect.html";
    }
    
    @GetMapping("/eventosdisponibles")
    public String eventosDisponibles() {

        return "eventosdisponibles.html";
    }
    
    @GetMapping("/consultas")
    public String consultas() {

        return "consultas.html";
    }
    

    @GetMapping("/registro")
    public String registro() {

        return "registro.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            
            model.put("error", "Usuario o Contraseña incorrectos");
        }
        if (logout != null) {
            model.put("logout","Desconectado correctamente");
        }

        return "login.html";
    }
    
    
    
    

}
