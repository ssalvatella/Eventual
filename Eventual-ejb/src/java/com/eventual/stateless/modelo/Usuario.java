/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import com.eventual.singleton.BaseDatosLocal;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Samuel
 */
@Stateless
public class Usuario {
    
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

    
}
