/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import com.eventual.singleton.BaseDatosLocal;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Samuel
 */
@Stateless
@LocalBean
public class PerfilSocial implements PerfilSocialRemote {
    
    @EJB
    private BaseDatosLocal bd;

    private String nombre;

    public PerfilSocial() {
    }
    
    public PerfilSocial(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * devuelvePerfil()
     * 
     * Devuelve el perfil con el id indicado por parámetro.
     * En caso de no encontrarlo devuelve null.
     * @param idUsuario
     * @return 
     */
    public PerfilSocial devuelvePerfil(int idUsuario) {
        
        try {
            String consulta = "SELECT * FROM perfil_social WHERE usuario_perfil = '" + idUsuario + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                String nombre = rs.getString("nombre_perfil");
                return new PerfilSocial(nombre);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerfilSocial.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getNombre() {
        return nombre;
    }
    
    
}
