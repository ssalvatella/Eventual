/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.websockets;

import com.eventual.singleton.AdministracionLocal;
import com.eventual.singleton.ChatLocal;
import com.eventual.singleton.UsuarioConectado;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ejb.EJB;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Samuel
 */
@ServerEndpoint("/administradorWS")
public class AdministradorWS {
    
    @EJB
    private AdministracionLocal admin;
    
    @EJB
    private ChatLocal chat;
    
    /*--------------- PARSEADOR JSON ---------------- */
    private final JsonParser parser = new JsonParser();

    @OnMessage
    public String onMessage(Session sesion, String mensaje) {
        return procesarMensaje(sesion, mensaje);
    }
    
    @OnClose
    public void onClose(Session sesion) {
        // Eliminamos la conexión de la lista de conexiones
        this.admin.eliminarConectado(sesion);
    }
    
    private String procesarMensaje(Session sesion, String mensaje) {
        
        JsonObject respuesta = new JsonObject();
        
        JsonObject json = this.parser.parse(mensaje).getAsJsonObject();
        json = json.getAsJsonObject("mensaje");
        ChatWS.TipoMensaje tipo = ChatWS.TipoMensaje.valueOf(json.get("tipo").getAsString());
        switch (tipo) {
            case CONEXION:
                // Leemos los datos de presentación
                int idUsuario = json.get("idUsuario").getAsInt();
                String nombre = json.get("nombre").getAsString();
                UsuarioConectado usuario = this.admin.devuelveConectado(idUsuario, nombre, sesion);
                respuesta = this.admin.devuelveConectados();
                // Añadimos el usuario a la lista de conectados
                this.admin.añadirConectado(usuario);
            break;
            case ADMINISTRADOR:
                respuesta.addProperty("tipo", "CONECTADOS");
                respuesta.addProperty("conectados", this.chat.getConectados().size());
            break;
            case EXPULSION:
                int id = json.get("id").getAsInt();
                this.chat.expulsarUsuario(id);
            break;
        }
        return respuesta.toString();
    }
}
