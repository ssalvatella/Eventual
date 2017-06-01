/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.webservices;

import com.eventual.singleton.GestorIdentificacionesLocal;
import com.eventual.stateless.modelo.UsuarioRemote;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Samuel
 */
@WebService(serviceName = "IdentificadorWebService")
public class IdentificadorWebService {
    
    @EJB
    private UsuarioRemote usuarios;
    
    @EJB
    private GestorIdentificacionesLocal identificaciones;
    
    private final SecureRandom random = new SecureRandom();


    /**
     * Web service operation
     */
    @WebMethod(operationName = "validar")
    public String validar(@WebParam(name = "email") final String email, @WebParam(name = "password") String password) {
        boolean usuarioValido = this.usuarios.valido(email, password);
        if (usuarioValido) {
            String token = generarToken();
            this.identificaciones.registrarToken(token);
            return token;
        } else {
            return null;
        }
    }

    private String generarToken() {
        return new BigInteger(130, random).toString(32);
    }
}
