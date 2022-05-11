/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.controladores;

import com.example.Eventbring.entidades.Asistencia;
import com.example.Eventbring.entidades.Evento;
import com.example.Eventbring.entidades.Usuario;
import com.example.Eventbring.servicios.AsistenciaServicio;
import com.example.Eventbring.servicios.EventoServicio;
import com.example.Eventbring.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private EventoServicio es;

    @Autowired
    private AsistenciaServicio as;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String login, ModelMap model) {
        if (login != null) {
            model.put("exito", "Logueado con exito");
        }

        return "proyect.html";
    }

    @GetMapping("/miperfil")
    public String miperfil() {

        return "miperfil.html";
    }

    @GetMapping("/registro")
    public String registro() {

        return "registro.html";
    }

    @GetMapping("/cargareventos")
    public String cargareventos() {

        return "cargareventos.html";
    }

    @GetMapping("/listaeventos")
    public String lista(ModelMap modelo, HttpSession session) {
        
        Usuario u = (Usuario) session.getAttribute("usuariosession");

        List<Evento> eventoLista = es.listarTodos();
        List<Evento> eventoListaFalse = new ArrayList<>();

        for (Evento evento : eventoLista) {
            evento.setAlta(Boolean.FALSE);
            
            if(!u.getUsername().equals(evento.getAnfitrion())){
            evento.setAutorizacion(Boolean.FALSE);
            }
            
            eventoListaFalse.add(evento);

        }

        List<Evento> eventosA = es.listarEventosALosQueAsistire();
        

        for (Evento evento : eventoListaFalse) {
            for (Evento e : eventosA) {
                if (e.getId_evento().equals(evento.getId_evento())) {
                    evento.setAlta(Boolean.TRUE);                 
                }
            }
        }

        modelo.addAttribute("eventos", eventoListaFalse);

        return "listaeventos.html";
    }

    @GetMapping("/listaasistencias")
    public String listaAsistencias(ModelMap modelo) {

        List<Evento> eventoLista = es.listarEventosALosQueAsistire();

        modelo.addAttribute("eventos", eventoLista);

        return "listaasistencias.html";
    }

    @GetMapping("/editperfil")
    public String editperfil() {

        return "editperfil.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {

            model.put("error", "Usuario o Contraseña incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Desconectado correctamente");
        }

        return "login.html";
    }

}
