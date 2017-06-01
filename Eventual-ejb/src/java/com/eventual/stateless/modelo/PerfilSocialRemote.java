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
public interface PerfilSocialRemote extends Serializable {

    /**
    * devuelve()
    * 
    * Devuelve el perfil con el id indicado por parámetro.
    * En caso de no encontrarlo devuelve null.
    * @param idUsuario
    * @return 
    */
    public PerfilSocial devuelve(int idUsuario);
    
    public List<Perfil> buscar(String campo);

    public String getNombre();
    
    public int getId();
    
    public double completitudPerfil(int id);
    
    public void actualizar(int id, String nacimiento, String ciudad, String estudios, 
            String profesion, String descripcion);
    
}
