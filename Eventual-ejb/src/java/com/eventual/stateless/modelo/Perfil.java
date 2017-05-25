/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

/**
 *
 * @author Samuel
 */
public abstract class Perfil {
    
    private int idUsuario;
    private String nombre;
    
    public Perfil(int idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }
    
    public Perfil() {
        
    }
    
    
    public int getId() {
        return this.idUsuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
}
