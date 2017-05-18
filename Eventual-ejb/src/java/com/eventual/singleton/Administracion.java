/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

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

}
