/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.webservices;

import com.eventual.stateless.modelo.Usuario;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@WebService(serviceName = "IdentificadorWS")
@Stateless()
public class IdentificadorWS {
    
    @EJB
    private Usuario usuario;

    /**
     * usuarioValido()
     * 
     * Devuelve 'true' si el email y contraseña corresponden a un usuario
     * válido y 'false' en caso contrario.
     */
    @WebMethod(operationName = "usuarioValido")
    public boolean usuarioValido(@WebParam(name = "usuario") final String email, 
            @WebParam(name = "contrase\u00f1a") final String contraseña) {
        return this.usuario.valido(email, contraseña);
    }
}
