/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilOrganizacion;
import com.eventual.stateless.modelo.PerfilOrganizacionRemote;
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
public class SesionOrganizacion implements SesionOrganizacionRemote {
    
    @EJB
    private PerfilOrganizacionRemote po;
    
    private Usuario usuario;
    private PerfilOrganizacion perfil;
    private boolean conectado;
    
    private String token;
    
    @Override
    public void conectar(Usuario usuario) {
        this.usuario = usuario;
        this.perfil = po.devuelve(usuario.getId());
        if (perfil != null)
            this.conectado = true;  
    }

    @Override
    public boolean conectado() {
        return usuario != null && conectado;
    }

    @Override
    public Usuario getUsuario() {
        return this.usuario;
    }

    @Override
    public PerfilOrganizacion getPerfil() {
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
