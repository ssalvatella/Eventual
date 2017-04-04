/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless;

import javax.ejb.Local;

/**
 *
 * @author Samuel
 */
@Local
public interface EjemploBeanLocal {
    
    public String saludar();
    
}
