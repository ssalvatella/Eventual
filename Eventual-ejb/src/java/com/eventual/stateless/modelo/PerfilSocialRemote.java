/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import java.io.Serializable;
import javax.ejb.Remote;

/**
 *
 * @author Samuel
 */
@Remote
public interface PerfilSocialRemote extends Serializable {
    
        /**
     * devuelvePerfil()
     * 
     * Devuelve el perfil con el id indicado por parámetro.
     * En caso de no encontrarlo devuelve null.
     * @param idUsuario
     * @return 
     */
    public PerfilSocial devuelvePerfil(int idUsuario);

    public String getNombre();
    
}
