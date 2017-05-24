/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import com.eventual.singleton.BaseDatosLocal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
@Remote
public class Post implements PostRemote {
    
    @EJB
    private BaseDatosLocal bd;

    @Override
    public void registrarPost(int idUsuario, String contenido) {
        try { 
            String consulta = "INSERT INTO post (usuario, contenido) VALUES (" +
                    idUsuario + ", '" + contenido + "');";
            Statement stm = bd.getStatement();
            stm.execute(consulta);
            // Notificar a admins...
        } catch (SQLException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
