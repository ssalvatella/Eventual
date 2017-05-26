/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import com.eventual.singleton.AdministracionLocal;
import com.eventual.singleton.BaseDatosLocal;
import com.ocpsoft.pretty.time.PrettyTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
@DependsOn(value="BaseDatosLocal")
public class Mensaje implements MensajeRemote {
    
    @EJB
    private AdministracionLocal admin;
    
    private int emisor;
    private int destinatario;
    private String texto;
    private String fecha;
    
    private final PrettyTime pt = new PrettyTime(new Locale("es"));

    public Mensaje() {
    }
    
    public Mensaje(int emisor, int destinatario, String texto, String fecha) {
        this.emisor = emisor;
        this.destinatario = destinatario;
        this.texto = texto;

        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            Date f = df.parse(fecha);
            this.fecha = pt.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @EJB
    private BaseDatosLocal bd;

    @Override
    public List<Mensaje> devuelve(int idUsuario, int idAmigo) {
        try {
            List<Mensaje> mensajes = new ArrayList<>();
            String consulta = "SELECT * FROM mensaje WHERE (emisor = '" + idUsuario + "' AND destinatario ='"+ idAmigo +"') "
                    + " OR (emisor ='" + idAmigo + "' AND destinatario = '" + idUsuario + "');";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            while (rs.next()) {
                
                int emisor = rs.getInt("emisor");
                int destinatario = rs.getInt("destinatario");
                String texto = rs.getString("texto");
                String fecha = rs.getString("fecha_mensaje");
                mensajes.add(new Mensaje(emisor, destinatario, texto, fecha));
            }
            return mensajes;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    @Override
    public void añadir(int emisor, int destinatario, String texto) {
        try { 
            String consulta = "INSERT INTO mensaje (emisor, destinatario, texto) VALUES (" +
                    emisor + ", " + destinatario + ", '" + texto + "');";
            Statement stm = bd.getStatement();
            stm.execute(consulta);
            admin.notificarNumeroMensajes();
        } catch (SQLException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
    @Override
    public int cuentaUltimosMensajes() {
        try {
            String consulta = "SELECT COUNT(id_mensaje) as numero_mensajes FROM mensaje "
                    + "WHERE (TIMESTAMPDIFF(MINUTE, fecha_mensaje,  NOW())) <= 120;";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                return rs.getInt("numero_mensajes");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } 
        return -1;
    }   


    public int getEmisor() {
        return emisor;
    }

    public int getDestinatario() {
        return destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public String getFecha() {
        return fecha;
    }

    public BaseDatosLocal getBd() {
        return bd;
    }

}
