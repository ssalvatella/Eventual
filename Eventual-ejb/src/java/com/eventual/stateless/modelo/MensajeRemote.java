/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Samuel
 */
@Remote
public interface MensajeRemote extends Serializable {
    
    public List<Mensaje> devuelve(int idUsuario, int idAmigo);
    
    public void añadir(int emisor, int destintario, String texto);
    
    public int cuentaUltimosMensajes();
    
}
