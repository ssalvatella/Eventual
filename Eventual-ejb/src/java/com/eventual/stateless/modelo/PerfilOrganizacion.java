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
    
    private final int NUMERO_CAMPOS = 5;
    
    private String ciudad;
    private String direccion;
    private String contacto;
    private String descripcion;
    private String categoria;
    
    @EJB
    private BaseDatosLocal bd;

    public PerfilOrganizacion() {
    }
    
    public PerfilOrganizacion(int idOrg, String nombreOrg, String ciudad, 
            String direccion, String contacto, String descripcion, String categoria) {
        super(idOrg, nombreOrg);
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.contacto = contacto;
        this.descripcion = descripcion;
        this.categoria = categoria;
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
                String ciudad = rs.getString("ciudad_organizacion");
                String direccion = rs.getString("direccion_organizacion");
                String contacto = rs.getString("contacto_organizacion");
                String descripcion = rs.getString("descripcion_organizacion");
                String categoria = rs.getString("categoria_organizacion");
                return new PerfilOrganizacion(idOrg, nombreOrg, ciudad, direccion, contacto, descripcion, categoria);
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
                String ciudad = rs.getString("ciudad_organizacion");
                String direccion = rs.getString("direccion_organizacion");
                String contacto = rs.getString("contacto_organizacion");
                String descripcion = rs.getString("descripcion_organizacion");
                String categoria = rs.getString("categoria_organizacion");
                resultados.add(new PerfilOrganizacion(id_usuario, nombre, ciudad, direccion, contacto, descripcion, categoria));
            }
            return resultados;
        } catch (SQLException ex) {
            Logger.getLogger(PerfilOrganizacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    @Override
    public double completitudPerfil(int id) {
        PerfilOrganizacion perfil = devuelve(id);
        if (perfil == null) return -1;
        int camposCompletos = 0;
        
        if (perfil.getCiudad() != null) {
            if (!perfil.getCiudad().equals("")) camposCompletos++;           
        }
        if (perfil.getDireccion() != null) {
            if (!perfil.getDireccion().equals("")) camposCompletos++;           
        }
        if (perfil.getContacto() != null) {
            if (perfil.getContacto() != null) camposCompletos++;           
        }
        if (perfil.getDescripcion() != null) {
            if (!perfil.getDescripcion().equals("")) camposCompletos++;           
        }
        if (perfil.getCategoria() != null) {
            if (!perfil.getCategoria().equals("")) camposCompletos++;           
        }
        return (double) camposCompletos / NUMERO_CAMPOS;
    }
    
    public double completitudPerfil() {
        int camposCompletos = 0;
        
        if (getCiudad() != null) {
            if (!getCiudad().equals("")) camposCompletos++;           
        }
        if (getDireccion() != null) {
            if (!getDireccion().equals("")) camposCompletos++;           
        }
        if (getContacto() != null) {
            if (getContacto() != null) camposCompletos++;           
        }
        if (getDescripcion() != null) {
            if (!getDescripcion().equals("")) camposCompletos++;           
        }
        if (getCategoria() != null) {
            if (!getCategoria().equals("")) camposCompletos++;           
        }
        return (double) camposCompletos / NUMERO_CAMPOS;
    }

    @Override
    public void actualizar(int id, String contacto, String descripcion, String categoria) {
        try {
            String consulta = "UPDATE perfil_organizacion SET "
                    + "contacto_organizacion='" + contacto + "', "
                    + "descripcion_organizacion='" + descripcion + "', "
                    + "categoria_organizacion='" + categoria
                    + "' WHERE id_organizacion=" + id + "; ";
            Statement stm = bd.getStatement();
            stm.executeUpdate(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(PerfilOrganizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }



    
    

}