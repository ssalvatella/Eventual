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
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
@DependsOn(value="BaseDatosLocal")
public class Evento implements EventoRemote {
    
    @EJB
    private BaseDatosLocal bd;

    @Override
    public void registrar(int organizacion, String fecha, String nombre, String descripcion) {
        
        String consulta = "INSERT INTO evento (organizacion, fecha_evento, nombre_evento, descripcion_evento) "
                + "VALUES (" + organizacion + ", '" + fecha + "', '" + nombre + "', '" + descripcion + "');";
        Statement stm = bd.getStatement();
        try {
            stm.execute(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


}
