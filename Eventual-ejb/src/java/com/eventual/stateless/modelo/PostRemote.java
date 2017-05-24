/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public interface PostRemote extends Serializable {
    
    public void registrarPost(int idUsuario, String contenido);
    
}
