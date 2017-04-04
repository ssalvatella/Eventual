/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless;

import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
public class EjemploBean implements EjemploBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public String saludar() {
        return "Hola mundo!";
    }
}
