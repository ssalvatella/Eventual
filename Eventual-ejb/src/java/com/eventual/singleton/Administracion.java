/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.Session;

/**
 *
 * @author Samuel
 */
@Singleton
public class Administracion implements AdministracionLocal {
    
    @EJB
    private ChatLocal chat;
    
    private Map<Integer, UsuarioConectado> administradores = new HashMap<>();

    @Override
    public Map<Integer, UsuarioConectado> getAdministradores() {
        return administradores;
    }
    
    @Override
    public UsuarioConectado devuelveConectado(int id, String nombre, Session sesion) {
        return new UsuarioConectado(id, nombre, sesion);
    }
    
    @Override
    public void añadirConectado(UsuarioConectado usuario) {
          administradores.put(usuario.getIdUsuario(), usuario);
    }

    @Override
    public void notificarNumeroUsuarios() {
        JsonObject notificacion = new JsonObject();
        administradores.values().forEach((admin) -> {
            try {
                notificacion.addProperty("tipo", "CONECTADOS");
                notificacion.addProperty("conectados", this.chat.getConectados().size());
                admin.getSesion().getBasicRemote().sendText(notificacion.toString());
            } catch (IOException ex) {
                Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    @Override
    public int eliminarConectado(Session sesion) {
        for (UsuarioConectado u : administradores.values()) {
            if (u.getSesion().getId().equals(sesion.getId())) {
                administradores.remove(u.getIdUsuario());
                return u.getIdUsuario();
            }
        }
        return -1;
    }
    
    @Override
    public JsonObject devuelveConectados() {
        JsonObject respuesta = new JsonObject();
        respuesta.addProperty("tipo", "LISTA_CONECTADOS");
        JsonArray conectados = new JsonArray();
        this.chat.getConectados().values().stream().map((u) -> {
            JsonObject elemento = new JsonObject();
            elemento.addProperty("id", u.getIdUsuario());
            elemento.addProperty("nombre", u.getNombre());
            return elemento;
        }).forEachOrdered((elemento) -> {
            conectados.add(elemento);
        });
        respuesta.add("lista", conectados);
        return respuesta;
    }

    @Override
    public void notificarNumeroMensajes() {
        JsonObject notificacion = new JsonObject();
        administradores.values().forEach((u) -> {
            try {
                notificacion.addProperty("tipo", "NUMERO_MENSAJES");
                u.getSesion().getBasicRemote().sendText(notificacion.toString());
            } catch (IOException ex) {
                Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void notificarNumeroPosts() {
        JsonObject notificacion = new JsonObject();
        administradores.values().forEach((u) -> {
            try {
                notificacion.addProperty("tipo", "NUMERO_POSTS");
                u.getSesion().getBasicRemote().sendText(notificacion.toString());
            } catch (IOException ex) {
                Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void notificarNuevoUsuario(UsuarioConectado u) {
        this.administradores.values().forEach((admin) -> {
            try {
                JsonObject notificacion = new JsonObject();
                notificacion.addProperty("tipo", "CONECTADO");
                notificacion.addProperty("id", u.getIdUsuario());
                notificacion.addProperty("nombre", u.getNombre());
                admin.getSesion().getBasicRemote().sendText(notificacion.toString());                
            } catch (IOException ex) {
                Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void notificarDesconexionUsuario(int desconectado) {
        this.administradores.values().forEach((u) -> {
            try {
                JsonObject notificacion = new JsonObject();
                notificacion.addProperty("tipo", "DESCONECTADO");
                notificacion.addProperty("id", desconectado);
                u.getSesion().getBasicRemote().sendText(notificacion.toString());
                
            } catch (IOException ex) {
                Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void notificarNuevoRegistro() {
        JsonObject notificacion = new JsonObject();
        administradores.values().forEach((u) -> {
            try {
                notificacion.addProperty("tipo", "NUEVO_REGISTRO");
                u.getSesion().getBasicRemote().sendText(notificacion.toString());
            } catch (IOException ex) {
                Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
