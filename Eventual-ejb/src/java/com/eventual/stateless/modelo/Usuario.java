/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import com.eventual.singleton.BaseDatosLocal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Samuel
 */
@Stateless
@DependsOn(value="BaseDatosLocal")
public class Usuario {
    
    public static enum TIPO {
        ADMINISTRADOR, SOCIAL, ORGANIZACIÓN
    }
    
    @EJB
    private BaseDatosLocal bd;

    /**
     * valido()
     * 
     * Comprueba si el email y contraseña pasados como parámetros son 
     * válidos de un usuario del sistema.
     * @param email
     * @param contraseña
     * @return 'true' si datos correctos, 'false' en caso contrario
     */
   public boolean valido(String email, String contraseña) {
       
        try {
            String consulta = "SELECT * FROM usuario WHERE email_usuario = '" + email + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) { // El usuario existe
                // Hash de la contraseña para verificar
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(contraseña.getBytes(StandardCharsets.UTF_8));
                String hashString = DatatypeConverter.printHexBinary(hashBytes);
                
                return hashString.equals(rs.getString("contraseña_usuario")); 
            } else { // Si el usuario no existe...
                return false;
            }
        } catch (NoSuchAlgorithmException | SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
   }
   
   /**
    * existeEmail()
    * 
    * Devuelve 'true' si existe el email pasado por parámetro y 'false' en 
    * caso contrario.
    * @param email
    * @return 
    */
   public boolean existeEmail(final String email) {
        try {
            String consulta = "SELECT * FROM usuario WHERE email_usuario = '" + email + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            return rs.next(); // Devuelve existencia de email...
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
   }

   /**
    * registrar()
    * 
    * Registra un usuario como perfil social en la base de datos.
    * Devuelve 'false' en caso de que no se realice la insercción.
    * 
    * @param email
    * @param contraseña
    * @param nombre
    * @return 
    */
   public boolean registrar(final String email, final String contraseña, 
           final String nombre) {
       try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(contraseña.getBytes(StandardCharsets.UTF_8));
            String hashString = DatatypeConverter.printHexBinary(hashBytes);
            String consulta = "INSERT INTO usuario (email_usuario, contraseña_usuario, tipo_usuario) VALUES ('" + 
                    email + "', '" + hashString + "', '" + TIPO.SOCIAL.name() + "');";
            Statement stm = bd.getStatement();
            int id_usuario = stm.executeUpdate(consulta, Statement.RETURN_GENERATED_KEYS);
            consulta = "INSERT INTO perfil_social (usuario_perfil, nombre_perfil) VALUES (" + id_usuario + ", '" + nombre +"');";
            return stm.execute(consulta);
        } catch (NoSuchAlgorithmException | SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
   }
    
}
