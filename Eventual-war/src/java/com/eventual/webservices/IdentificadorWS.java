/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.webservices;

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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "usuarioValido")
    public boolean usuarioValido(@WebParam(name = "usuario") final String usuario, 
            @WebParam(name = "contrase\u00f1a") final String contraseña) {
        //TODO write your implementation code here:
        return false;
    }
}
