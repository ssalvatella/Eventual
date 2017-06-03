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
 * @author Pablo
 */
@Remote
public interface PerfilOrganizacionRemote extends Serializable {

    /**
    * devuelve()
    * 
    * Devuelve el perfil con el id indicado por parámetro.
    * En caso de no encontrarlo devuelve null.
    * @param idOrg
    * @return 
    */
    public PerfilOrganizacion devuelve(int idOrg);
    
    public List<Perfil> buscar(String campo);
   

    
}
