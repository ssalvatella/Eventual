/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import com.eventual.stateless.modelo.MensajeRemote;
import com.eventual.stateless.modelo.UsuarioRemote;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.Session;

/**
 *
 * @author Samuel
 */
@Singleton
public class Chat implements ChatLocal {
    
    @EJB
    private AdministracionLocal admin;
    
    @EJB
    private UsuarioRemote usr;
    
    @EJB
    private MensajeRemote mensajes;
    
    private Map<Integer, UsuarioConectado> conectados = new HashMap<>();
     

    @Override
    public void añadirConectado(UsuarioConectado usuario) {
        conectados.put(usuario.getIdUsuario(), usuario);
        admin.notificarNumeroUsuarios(); // Notificamos a los admins
    }

    @Override
    public JsonArray devuelveAmigosConectados(UsuarioConectado usuario) {
        JsonArray amigosConectados = new JsonArray();
        for (int idAmigo : usuario.getAmigos()) {
            UsuarioConectado amigo = conectados.get(idAmigo);
            if (amigo != null) {
                JsonObject elemento = new JsonObject();
                elemento.addProperty("id", amigo.getIdUsuario());
                elemento.addProperty("nombre", amigo.getNombre());
                amigosConectados.add(elemento);
            }
        }
        return amigosConectados;
    }
    
    @Override
    public UsuarioConectado devuelveConectado(int id, String nombre, Session sesion) {
        List<Integer> amigos = this.usr.devuelveIdsAmigos(id);
        return new UsuarioConectado(id, nombre, amigos.stream().mapToInt(i->i).toArray(), sesion);
    }

    @Override
    public int eliminarConectado(Session sesion) {
        for (UsuarioConectado u : conectados.values()) {
            if (u.getSesion().getId().equals(sesion.getId())) {
                conectados.remove(u.getIdUsuario());
                admin.notificarNumeroUsuarios(); // Notificamos a los admins
                return u.getIdUsuario();
            }
        }

        return -1;
    }

    @Override
    public Map<Integer, UsuarioConectado> getConectados() {
        return conectados;
    }

    @Override
    public void registrarMensaje(int emisor, int destintario, String texto) {
        this.mensajes.añadir(emisor, destintario, texto);
    }
    
    
    
    
}
