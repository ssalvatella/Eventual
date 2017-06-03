/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

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
public class Evento implements EventoRemote {
    
    @EJB
    private BaseDatosLocal bd;
    
    @EJB
    private UsuarioRemote usuarios;
    
    private final PrettyTime pt = new PrettyTime(new Locale("es"));

    public Evento() {
    }
    
    private int id;
    private String nombre;
    private String fecha;
    private String descripcion;
    private int invitados;
    
    public Evento(int id, String nombre, String fecha, String descripcion, int invitados) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.invitados = invitados;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            Date f = df.parse(fecha);
            this.fecha = pt.format(f);
        } catch (ParseException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registrar(int organizacion, String fecha, String nombre, String descripcion) {
        
        String consulta = "INSERT INTO evento (organizacion, fecha_evento, nombre_evento, descripcion_evento) "
                + "VALUES (" + organizacion + ", STR_TO_DATE('" + fecha + "', '%d/%m/%Y'), '" + nombre + "', '" + descripcion + "');";
        Statement stm = bd.getStatement();
        try {
            stm.execute(consulta);
            String consulta2 = "SELECT id_evento FROM evento WHERE (organizacion=" +  organizacion + " AND nombre_evento='" + nombre +"');";
            ResultSet rs = stm.executeQuery(consulta2);
            if (rs.next()) {
                int id_evento = rs.getInt("id_evento");
                invitarAmigos(organizacion, id_evento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void invitarAmigos(int organizacion, int idEvento) {
        
        List<Integer> amigos = this.usuarios.devuelveIdsAmigos(organizacion);
        amigos.forEach((_item) -> {
            try {
                String consulta = "INSERT INTO invitacion_evento (usuario_invitacion, evento_invitacion) "
                        + "VALUES (" + organizacion +", "+ idEvento + ");";
                bd.getStatement().execute(consulta);
            } catch (SQLException ex) {
                Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public List<Evento> devuelveEventosOrg(int id) {
        List<Evento> resultado = new ArrayList<>();
        try {
            String consulta = "SELECT *, COUNT(usuario_invitacion) as invitados FROM evento "
                    + "LEFT JOIN invitacion_evento ON id_evento=evento_invitacion "
                    + "WHERE organizacion=" + id + " GROUP BY id_evento ASC ORDER BY fecha_evento DESC;";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            while (rs.next()) {
                int idevento = rs.getInt("id_evento");
                String nombre = rs.getString("nombre_evento");
                String fecha = rs.getString("fecha_evento");
                String descripcion = rs.getString("descripcion_evento");
                int invitados = rs.getInt("invitados");
                resultado.add(new Evento(idevento, nombre, fecha, descripcion, invitados));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getInvitados() {
        return invitados;
    }

    

}
