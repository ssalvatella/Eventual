/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.websockets;

import com.eventual.singleton.ChatLocal;
import com.eventual.singleton.UsuarioConectado;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Samuel
 */
@Stateless
@ServerEndpoint("/chat")
public class ChatWS {
    
    @EJB
    private ChatLocal chat;
    
    /*--------------- PARSEADOR JSON ---------------- */
    private final JsonParser parser = new JsonParser();
    
    
    /**
     * MENSAJES
     */
    private enum TipoMensaje {
        CONEXION, CONECTADOS, MENSAJE, ADMINISTRADOR
    }

    @OnMessage
    public String onMessage(Session sesion, String mensaje) {
        return procesarMensaje(sesion, mensaje);
    }
    
    private String procesarMensaje(Session sesion, String mensaje) {
        
        JsonObject respuesta = new JsonObject();
        
        JsonObject json = this.parser.parse(mensaje).getAsJsonObject();
        json = json.getAsJsonObject("mensaje");
        TipoMensaje tipo = TipoMensaje.valueOf(json.get("tipo").getAsString());
        switch (tipo) {
            case CONEXION:
                // Leemos los datos de presentación
                int idUsuario = json.get("idUsuario").getAsInt();
                String nombre = json.get("nombre").getAsString();
                UsuarioConectado usuario = this.chat.devuelveConectado(idUsuario, nombre, sesion);
                
                // Respondemos con los amigos que esten conectados
                respuesta.addProperty("tipo", "CONECTADOS");
                JsonArray amigosConectados = chat.devuelveAmigosConectados(usuario);
                respuesta.add("conectados", amigosConectados);
                
                // Notificamos la conexión al resto de amigos
                notificarConexion(usuario);
                
                // Añadimos el usuario a la lista de conectados
                chat.añadirConectado(usuario);
            break;
            case MENSAJE:
                // Leemos los datos del mensaje
                int emisor = json.get("emisor").getAsInt();
                int destinatario = json.get("destinatario").getAsInt();
                String texto = json.get("mensaje").getAsString();
                
                // Notificamos el mensaje al destintario
                notificarMensaje(emisor, destinatario, texto);
                // Registramos el mensaje en la BD
                this.chat.registrarMensaje(emisor, destinatario, texto);
            break;
            case ADMINISTRADOR:
                respuesta.addProperty("conectados", this.chat.getConectados().size());
            break;
        }
        return respuesta.toString();
    }

    @OnClose
    public void onClose(Session sesion) {
        // Eliminamos la conexión de la lista de conexiones
        int idEliminado = this.chat.eliminarConectado(sesion);
        // Notificamos al resto de amigos que este usuario se ha desconectado
        notificarDesconexion(idEliminado);
        
    }
    
    /**
     * notificarConexion()
     * 
     * Recorre los usuarios que esten conectados y les notifica
     * de una nueva conexión en caso de que sea amigo.
     * @param usuario 
     */
    private void notificarConexion(UsuarioConectado usuario) {
        this.chat.getConectados().values().forEach((u) -> {
            try {
                // Si es amigo...
                if (IntStream.of(u.getAmigos()).anyMatch(x -> x == usuario.getIdUsuario())) {
                    JsonObject notificacion = new JsonObject();
                    notificacion.addProperty("tipo", "CONECTADO");
                    notificacion.addProperty("id", usuario.getIdUsuario());
                    notificacion.addProperty("nombre", usuario.getNombre());
                    u.getSesion().getBasicRemote().sendText(notificacion.toString());
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    /**
     * notificarDesconexion()
     * 
     * Recorre los usuarios y notifica la desconexión del id especificado.
     * @param eliminado 
     */
    private void notificarDesconexion(int eliminado) {
        this.chat.getConectados().values().forEach((u) -> {
            try {
                // Si es amigo...
                if (IntStream.of(u.getAmigos()).anyMatch(x -> x == eliminado)) {
                    JsonObject notificacion = new JsonObject();
                    notificacion.addProperty("tipo", "DESCONECTADO");
                    notificacion.addProperty("id", eliminado);
                    u.getSesion().getBasicRemote().sendText(notificacion.toString());
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void notificarMensaje(int emisor, int destintario, String texto) {
        
        UsuarioConectado usuario = this.chat.getConectados().get(destintario);
        if (usuario != null) { try {
            // Conectado
            JsonObject mensaje = new JsonObject();
            mensaje.addProperty("tipo", "MENSAJE");
            mensaje.addProperty("emisor", emisor);
            mensaje.addProperty("destintario", destintario);
            mensaje.addProperty("texto", texto);
            usuario.getSesion().getBasicRemote().sendText(mensaje.toString());
            } catch (IOException ex) {
                Logger.getLogger(ChatWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
