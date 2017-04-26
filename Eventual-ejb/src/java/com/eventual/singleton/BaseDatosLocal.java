/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import java.sql.Connection;
import java.sql.Statement;
import javax.ejb.Local;

/**
 *
 * @author Samuel
 */
@Local
public interface BaseDatosLocal {
    
    /**
     * getConnection()
     * 
     * @return objeto de conexión a la bd SQL
     */
    public Connection getConnection();
    
    /**
     * getStatement()
     * 
     * @return objeto statement conectado con la bd SQL
     */
    public Statement getStatement();
    
    /**
     * estaConectado()
     * 
     * @return devuelve 'true' si la bd esta conectada y
     * 'false' en caso contrario
     */
    public boolean estaConectado();
    
}
