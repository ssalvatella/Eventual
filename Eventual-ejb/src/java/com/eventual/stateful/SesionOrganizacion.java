/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilOrganizacion;
import com.eventual.stateless.modelo.PerfilOrganizacionRemote;
import com.eventual.stateless.modelo.Usuario;
import javax.ejb.EJB;

/**
 *
 * @author Samuel
 */
public class SesionOrganizacion {
    
    @EJB
    private PerfilOrganizacionRemote po;
    
    private Usuario usuario;
    private PerfilOrganizacion perfil;
    private boolean conectado;
    
    private String token;
    
    public void conectar(Usuario usuario) {
        this.usuario = usuario;
        this.perfil = po.devuelve(usuario.getId());
        if (perfil != null)
            this.conectado = true;  
    }
    
    public void setPerfil(PerfilOrganizacion perfil) {
        this.perfil = perfil;
    }
    
    public boolean conectado() {
        return usuario != null && conectado;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public PerfilOrganizacion getPerfil() {
        return this.perfil;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }


}
