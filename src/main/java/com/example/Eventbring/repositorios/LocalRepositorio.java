/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Eventbring.repositorios;

import com.example.Eventbring.entidades.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *

 * @author Irina
 */
@Repository
public interface LocalRepositorio extends JpaRepository<Local, String> {
     @Query("SELECT u FROM Local u WHERE u.id_local = :id_local")
    public Local buscarPorId(@Param("id_local")String id_local);

 
    @Query("SELECT u FROM Local u WHERE u.direccion = :direccion")
    public Local buscarPorDireccion(@Param("direccion")String direccion);
    

}
