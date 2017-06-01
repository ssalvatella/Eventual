/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateful;

import com.eventual.stateless.modelo.PerfilAdministrador;
import com.eventual.stateless.modelo.Usuario;
import java.io.Serializable;
import javax.ejb.Remote;

/**
 *
 * @author Samuel
 */
@Remote
public interface SesionAdministradorRemote extends Serializable {
    
    public void conectar(Usuario usuario);
    
    public boolean conectado();
    
    public Usuario getUsuario();

    public PerfilAdministrador getPerfil();
    
    public void setToken(final String token);
    
}
