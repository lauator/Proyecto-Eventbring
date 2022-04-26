/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.controladores;

import com.example.Eventbring.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Irina
 */
@Controller
@RequestMapping("")
public class MainController {
     @Autowired
    private UsuarioServicio us;
}
