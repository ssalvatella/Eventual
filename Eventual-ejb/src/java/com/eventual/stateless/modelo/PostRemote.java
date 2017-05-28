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
public interface PostRemote extends Serializable {
    
    public void registrarPost(int idUsuario, String contenido);
    
    /**
     * devuelve
     * 
     * Dado un id, una cantidad y un salto devuelve el 
     * bloque de posts correspondiente a ese usuario.
     * @param id
     * @param cantidad
     * @param salto
     * @return 
     */
    public List<Post> devuelve(int id, int cantidad, int salto);
    
    public int cuentaUltimosPosts();
    
    public void añadirMeGusta(int usuario, int post);
    
    public void quitarMeGusta(int usuario, int post);
    
}
