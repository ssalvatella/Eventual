/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilSocial;
import com.eventual.stateless.modelo.Usuario;

/**
 *
 * @author Samuel
 */
public class SesionSocial {
    
    
    private Usuario usuario;
    private PerfilSocial perfil;
    private boolean conectado;
    
    private String token;

    public void conectarUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (perfil != null)
            this.conectado = true;        
    }
    
    public void setPerfil(PerfilSocial perfil) {
        this.perfil = perfil;
    }

    public boolean usuarioConectado() {
        return usuario != null && conectado;       
    }

    public boolean esSocial() {
        return usuario.getTipo() == Usuario.TIPO.SOCIAL;       
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public PerfilSocial getPerfil() {
        return this.perfil;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return this.token;
    }

    
}
