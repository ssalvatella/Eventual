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
    
    private final int NUMERO_CAMPOS = 5;
    
    private String estudios;
    private String ciudad;
    private String profesion;
    private String nacimiento;
    private String descripcion;
    
    @EJB
    private BaseDatosLocal bd;

    public PerfilSocial() {
    }
    
    public PerfilSocial(int idUsuario, String nombre, String nacimiento, 
            String ciudad, String estudios, String profesion, String descripcion) {
        super(idUsuario, nombre);
        this.nacimiento = nacimiento;
        this.ciudad = ciudad;
        this.estudios = estudios;
        this.profesion = profesion;
        this.descripcion = descripcion;
    }
    
    /**
     * devuelve()
     * 
     * Devuelve el perfil con el id indicado por parámetro.
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
                String nacimiento = rs.getString("nacimiento_perfil");
                String ciudad = rs.getString("ciudad_perfil");
                String estudios = rs.getString("estudios_perfil");
                String profesion = rs.getString("profesion_perfil");
                String descripcion = rs.getString("descripcion_perfil");
                return new PerfilSocial(idUsuario, nombre, nacimiento, ciudad, estudios, profesion, descripcion);
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
                String nacimiento = rs.getString("nacimiento_perfil");
                String ciudad = rs.getString("ciudad_perfil");
                String estudios = rs.getString("estudios_perfil");
                String profesion = rs.getString("profesion_perfil");
                String descripcion = rs.getString("descripcion_perfil");
                resultados.add(new PerfilSocial(id_usuario, nombre, nacimiento, ciudad, estudios, profesion, descripcion));
            }
            return resultados;
        } catch (SQLException ex) {
            Logger.getLogger(PerfilSocial.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }

    @Override
    public double completitudPerfil(int id) {
        PerfilSocial perfil = devuelve(id);
        if (perfil == null) return -1;
        int camposCompletos = 0;
        
        if (!perfil.getEstudios().equals("")) camposCompletos++;
        if (!perfil.getCiudad().equals("")) camposCompletos++;
        if (perfil.getNacimiento() != null) camposCompletos++;
        if (!perfil.getDescripcion().equals("")) camposCompletos++;
        if (!perfil.getProfesion().equals("")) camposCompletos++;
        
        return (double) camposCompletos / NUMERO_CAMPOS;
    }
    
    public double devuelveCompletitud() {
        int camposCompletos = 0;

        if (!this.getEstudios().equals("")) camposCompletos++;
        if (!this.getCiudad().equals("")) camposCompletos++;
        if (this.getNacimiento() != null) camposCompletos++;
        if (!this.getDescripcion().equals("")) camposCompletos++;
        if (!this.getProfesion().equals("")) camposCompletos++;

        return (double) camposCompletos / NUMERO_CAMPOS;        
    }

    @Override
    public void actualizar(int id, String nacimiento, String ciudad, String estudios, String profesion, String descripcion) {
       if (nacimiento.equals("")) nacimiento = "NULL";
       String consulta = "UPDATE perfil_social SET "
               + "nacimiento_perfil=" + nacimiento + ", "
               + "ciudad_perfil='" + ciudad + "', "
               + "estudios_perfil='" + estudios + "', "
               + "profesion_perfil='" + profesion + "', "
               + "descripcion_perfil='" + descripcion + "' " 
               + "WHERE usuario_perfil=" + id + "; ";
       
       Statement stm = bd.getStatement();
        try {
            stm.execute(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(PerfilSocial.class.getName()).log(Level.SEVERE, null, ex);
        }           
    }
    
    public String getEstudios() {
        return estudios;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getProfesion() {
        return profesion;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    
    
    
    
}
