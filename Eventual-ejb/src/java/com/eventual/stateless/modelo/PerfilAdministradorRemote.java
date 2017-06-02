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
public interface PerfilAdministradorRemote extends Serializable {
    
    public PerfilAdministrador devuelve(int id);
    
}
