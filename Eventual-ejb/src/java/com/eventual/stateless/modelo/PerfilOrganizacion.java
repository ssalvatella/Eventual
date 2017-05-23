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
public class PerfilOrganizacion implements PerfilOrganizacionRemote {
    
    @EJB
    private BaseDatosLocal bd;

    private int idOrg;
    private String nombreOrg;

    public PerfilOrganizacion() {
    }
    
    public PerfilOrganizacion(int idOrg, String nombreOrg) {
        this.idOrg = idOrg;
        this.nombreOrg = nombreOrg;
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
            String consulta = "SELECT * FROM perfil_social WHERE usuario_perfil = '" + idOrg + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                String nombreOrg = rs.getString("nombre_org");
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
    public List<PerfilOrganizacion> buscar(String campo) {
        try {
            String consulta = "SELECT * FROM perfil_social "
                    + "WHERE nombre_perfil LIKE '%" + campo + "%';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            List<PerfilOrganizacion> resultados = new ArrayList<>();
            while (rs.next()) {
                int id_usuario = rs.getInt("usuario_perfil");
                String nombre = rs.getString("nombre_perfil");
                resultados.add(new PerfilOrganizacion(id_usuario, nombre));
            }
            return resultados;
        } catch (SQLException ex) {
            Logger.getLogger(PerfilOrganizacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
 
    @Override
    public int getIdOrg() {
        return this.idOrg;
    }
    @Override
    public String getNombreOrg() {
        return this.nombreOrg;
    }   
    
}