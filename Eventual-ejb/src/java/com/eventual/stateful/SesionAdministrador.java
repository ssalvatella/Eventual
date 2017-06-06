/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilAdministrador;
import com.eventual.stateless.modelo.PerfilAdministradorRemote;
import com.eventual.stateless.modelo.Usuario;
import javax.ejb.EJB;

/**
 *
 * @author Samuel
 */
public class SesionAdministrador {
    
    @EJB
    private PerfilAdministradorRemote pa;
    
    private Usuario usuario;
    private PerfilAdministrador perfil;
    private boolean conectado = false;
    
    private String token;

    public void conectar(Usuario usuario) {
        this.usuario = usuario;
        this.perfil = pa.devuelve(usuario.getId());
        if (perfil != null) {
            conectado = true;
        }
    }
    
    public void setPerfil(PerfilAdministrador perfil) {
        this.perfil = perfil;
    }
    
    public boolean conectado() {
        return usuario != null && conectado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public PerfilAdministrador getPerfil() {
        return perfil;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
}
