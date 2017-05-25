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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
public class PerfilAdministrador extends Perfil implements PerfilAdministradorRemote {
    
    
    @EJB
    private BaseDatosLocal bd;

    public PerfilAdministrador() {
    }
    
    public PerfilAdministrador(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public PerfilAdministrador devuelve(int idUsuario) {
        try {
            String consulta = "SELECT * FROM perfil_administrador WHERE usuario_perfil = '" + idUsuario + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                String nombre = rs.getString("nombre_perfil");
                return new PerfilAdministrador(idUsuario, nombre);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerfilSocial.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   
    
}
