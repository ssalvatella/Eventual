/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import com.eventual.singleton.BaseDatosLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
@LocalBean
public class PerfilSocial extends Perfil implements PerfilSocialRemote {
    
    @EJB
    private BaseDatosLocal bd;

    public PerfilSocial() {
    }
    
    public PerfilSocial(int idUsuario, String nombre) {
        super(idUsuario, nombre);
    }
    
    /**
     * devuelve()
 
 Devuelve el perfil con el id indicado por parámetro.
     * En caso de no encontrarlo devuelve null.
     * @param idUsuario
     * @return 
     */
    public PerfilSocial devuelve(int idUsuario) {
        
        try {
            String consulta = "SELECT * FROM perfil_social WHERE usuario_perfil = '" + idUsuario + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                String nombre = rs.getString("nombre_perfil");
                return new PerfilSocial(idUsuario, nombre);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerfilSocial.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    @Override
    public List<Perfil> buscar(String campo) {
        try {
            String consulta = "SELECT * FROM perfil_social "
                    + "WHERE nombre_perfil LIKE '%" + campo + "%';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            List<Perfil> resultados = new ArrayList<>();
            while (rs.next()) {
                int id_usuario = rs.getInt("usuario_perfil");
                String nombre = rs.getString("nombre_perfil");
                resultados.add(new PerfilSocial(id_usuario, nombre));
            }
            return resultados;
        } catch (SQLException ex) {
            Logger.getLogger(PerfilSocial.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    
}
