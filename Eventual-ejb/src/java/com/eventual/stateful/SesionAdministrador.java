/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilAdministrador;
import com.eventual.stateless.modelo.PerfilAdministradorRemote;
import com.eventual.stateless.modelo.Usuario;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
@DependsOn(value="BaseDatosLocal")
public class SesionAdministrador implements SesionAdministradorRemote {
    
    @EJB
    private PerfilAdministradorRemote pa;
    
    private Usuario usuario;
    private PerfilAdministrador perfil;
    private boolean conectado = false;
    
    private String token;

    @Override
    public void conectar(Usuario usuario) {
        this.usuario = usuario;
        this.perfil = pa.devuelve(usuario.getId());
        if (perfil != null) {
            conectado = true;
        }
    }

    @Override
    public boolean conectado() {
        return usuario != null && conectado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public PerfilAdministrador getPerfil() {
        return perfil;
    }
    
    @Override
    public void setToken(String token) {
        this.token = token;
    }
    
}
