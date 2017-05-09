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
public class Usuario implements UsuarioRemote {
    
    public static enum TIPO {
        ADMINISTRADOR, SOCIAL, ORGANIZACIÓN
    }
    
    
    @EJB
    private BaseDatosLocal bd;
    
    private int id;
    private String email;
    private TIPO tipo;
    private String fechaRegistro;
    private String ultimaModificacion;

    public Usuario() {
        
    }
    
    /**
     * Usuario()
     * 
     * Constructor que se utilizará como "contenedor" de los campos
     * del usuario.
     * 
     * @param email
     * @param tipo de usuario
     * @param fechaRegistro
     * @param ultimaModificacion 
     */
    public Usuario(int id, String email, TIPO tipo, 
            String fechaRegistro, String ultimaModificacion) {
        this.id = id;
        this.email = email;
        this.tipo = tipo;
        this.fechaRegistro = fechaRegistro;
        this.ultimaModificacion = ultimaModificacion;
    }

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
            stm.executeUpdate(consulta);
            String consulta_id = "SELECT * FROM usuario WHERE email_usuario = '" + email + "';";
            ResultSet rs = stm.executeQuery(consulta_id);
            if (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                consulta = "INSERT INTO perfil_social (usuario_perfil, nombre_perfil) VALUES (" + id_usuario + ", '" + nombre +"');";
                return stm.execute(consulta);
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException | SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
   }
   
/**
 * devuelveUsuario()
 * 
 * Devuelve el usuario con el email pasado por parámetro.
 * @param email
 * @return 
 */
   public Usuario devuelveUsuario(String email) {
       try {
            String consulta = "SELECT * FROM usuario WHERE email_usuario = '" + email + "';";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String emailUsuario = rs.getString("email_usuario");
                TIPO tipoUsuario = TIPO.valueOf(rs.getString("tipo_usuario"));
                String registroUsuario = rs.getString("registro_usuario");
                String ultimaModificacion = rs.getString("ultima_modificacion_usuario");
                return new Usuario(idUsuario, emailUsuario, tipoUsuario, registroUsuario, ultimaModificacion);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
   }

    public int getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }

    public TIPO getTipo() {
        return tipo;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }
   
   
   
}
