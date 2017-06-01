/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilSocial;
import com.eventual.stateless.modelo.PerfilSocialRemote;
import com.eventual.stateless.modelo.Usuario;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Samuel
 */
@Stateful
@SessionScoped
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 20)
public class SesionSocial implements SesionSocialRemote {
    
    @EJB
    private PerfilSocialRemote ps;
    
    private Usuario usuario;
    private PerfilSocial perfil;
    private boolean conectado;
    
    private String token;

    @Override
    public void conectarUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.perfil = ps.devuelve(usuario.getId());
        if (perfil != null)
            this.conectado = true;        
    }

    @Override
    public boolean usuarioConectado() {
        return usuario != null && conectado;       
    }

    @Override
    public boolean esSocial() {
        return usuario.getTipo() == Usuario.TIPO.SOCIAL;       
    }

    @Override
    public Usuario getUsuario() {
        return this.usuario;
    }

    @Override
    public PerfilSocial getPerfil() {
        return this.perfil;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
    
    @Override
    public String getToken() {
        return this.token;
    }

    
}
