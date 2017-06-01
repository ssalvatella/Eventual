/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilSocial;
import com.eventual.stateless.modelo.Usuario;
import java.io.Serializable;
import javax.ejb.Remote;

/**
 *
 * @author Samuel
 */
@Remote
public interface SesionSocialRemote extends Serializable {
    
    public void conectarUsuario(Usuario usuario);
    public boolean usuarioConectado();
    public boolean esSocial();
    public Usuario getUsuario();
    public PerfilSocial getPerfil();
    public void setToken(final String token);
    public String getToken();
    
}
