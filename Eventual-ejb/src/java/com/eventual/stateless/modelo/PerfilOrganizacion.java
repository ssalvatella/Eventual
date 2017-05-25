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
 * @author Pablo
 */
@Stateless
@LocalBean
public class PerfilOrganizacion extends Perfil implements PerfilOrganizacionRemote {
    
    @EJB
    private BaseDatosLocal bd;

    public PerfilOrganizacion() {
    }
    
    public PerfilOrganizacion(int idOrg, String nombreOrg) {
        super(idOrg, nombreOrg);
    }
    
    /**
     * devuelve()
     * 
     * Devuelve el perfil con el id indicado por parámetro.
     * En caso de no encontrarlo devuelve null.
     * @param idOrg
     * @return 
     */
    @Override
    public PerfilOrganizacion devuelve(int idOrg) {
        
        //CAMBIAR CONSULTA Y CREAR TABLA EN LA BD DE ORGANIZACIONES
        
        try {
            String consulta = "SELECT * FROM perfil_organizacion WHERE id_organizacion = '" + idOrg + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                String nombreOrg = rs.getString("nombre_organizacion");
                return new PerfilOrganizacion(idOrg, nombreOrg);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerfilOrganizacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Perfil> buscar(String campo) {
        try {
            String consulta = "SELECT * FROM perfil_organizacion "
                    + "WHERE nombre_organizacion LIKE '%" + campo + "%';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            List<Perfil> resultados = new ArrayList<>();
            while (rs.next()) {
                int id_usuario = rs.getInt("id_organizacion");
                String nombre = rs.getString("nombre_organizacion");
                resultados.add(new PerfilOrganizacion(id_usuario, nombre));
            }
            return resultados;
        } catch (SQLException ex) {
            Logger.getLogger(PerfilOrganizacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }

}