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
public interface EventoRemote extends Serializable {
    
    public void registrar(int organizacion, String fecha, String nombre, String descripcion);
    
    public List<Evento> devuelveEventosOrg(int id);
    
    public Evento devuelve(int id);
    
}
