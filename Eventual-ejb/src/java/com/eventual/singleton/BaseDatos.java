/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import java.sql.Connection;
import java.sql.Statement;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
@Startup
@Singleton
public class BaseDatos implements BaseDatosLocal {
    
    private Connection conexion;
    private Statement statement;
    
    private boolean conectado = false;
    
    /**
     * Datos de conexión
     */
    private final String USUARIO_BD = "root";
    private final String HOST_BD = "localhost";
    
    @PostConstruct
    public void iniciar() {
        try {
            conectar();
            this.conectado = true;
        } catch (SQLException ex) {
            this.conectado = false;
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Connection getConnection() {
        return this.conexion;
    }

    @Override
    public Statement getStatement() {
        return this.statement;
    }
    
    @Override
    public boolean estaConectado() {
        return this.conectado;
    }
    
    private void conectar() throws SQLException {
        
        MysqlDataSource datosConexion = new MysqlDataSource();
        datosConexion.setUser(USUARIO_BD);
        datosConexion.setServerName(HOST_BD);
        
        // Conectamos
        this.conexion = datosConexion.getConnection();
        this.statement = this.conexion.createStatement();
    }

}
