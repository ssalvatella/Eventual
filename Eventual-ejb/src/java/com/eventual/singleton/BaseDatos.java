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
import javax.annotation.PreDestroy;

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
    private final String HOST_BD = "155.210.68.154";
    private final String BD_NOMBRE = "eventual";
    private final String CONTRASEÑA = "alumno";
    
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
    
    @PreDestroy
    public void finalizar() {
        try {
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.conectado = false;
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
    
    /**
     * conectar()
     * 
     * Realiza la conexión con la base de datos MySQL con los
     * datos definidos en las constantes de la conexión. En caso
     * de error lanza una SQLException.
     * @throws SQLException 
     */
    private void conectar() throws SQLException {
        
        MysqlDataSource datosConexion = new MysqlDataSource();
        datosConexion.setUser(USUARIO_BD);
        datosConexion.setPassword(CONTRASEÑA);
        datosConexion.setServerName(HOST_BD);
        datosConexion.setDatabaseName(BD_NOMBRE);
        
        // Conectamos
        this.conexion = datosConexion.getConnection();
        this.statement = this.conexion.createStatement();
    }
    
    /**
     * desconectar()
     * 
     * Cierra la conexión con la base de datos y el statement
     * abierto.
     * @throws SQLException 
     */
    private void desconectar() throws SQLException {
        this.statement.close();
        this.conexion.close();
    }

}
