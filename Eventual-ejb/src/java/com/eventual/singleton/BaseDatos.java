/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Samuel
 */
@Startup
@Singleton
public class BaseDatos implements BaseDatosLocal {
    
    private final String JNDI_POOL = "jdbc/eventual";
    
    private Connection conexion;
    private Statement statement;
    
    private DataSource fuenteDatos;
    
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
        } catch (NamingException ex) {
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
        try {
            return this.fuenteDatos.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Statement getStatement() {
        try {
            if (this.statement == null) {
                return conectar();
            } else if (this.statement.isClosed()) {
                return conectar();
            } else {
                return this.statement;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
    private Statement conectar() throws SQLException, NamingException {

        Context contexto = new InitialContext();
        //ConnectionPoolDataSource cpds = (ConnectionPoolDataSource)contexto.lookup(JNDI_POOL);
        //PooledConnection pc = cpds.getPooledConnection(); 
        this.fuenteDatos = (DataSource) contexto.lookup(JNDI_POOL);
        // Conectamos
        this.conexion = fuenteDatos.getConnection(); 
        this.statement = this.conexion.createStatement();
        return statement;
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
