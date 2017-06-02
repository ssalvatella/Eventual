/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;
import com.eventual.stateless.modelo.Post;
import com.google.gson.JsonArray;
import java.util.Map;
import javax.ejb.Local;
import javax.websocket.Session;

/**
 *
 * @author Samuel
 */
@Local
public interface ChatLocal {
    
    
    public void añadirConectado(UsuarioConectado usuario);
    
    public JsonArray devuelveAmigosConectados(UsuarioConectado usuario);
    
    public UsuarioConectado devuelveConectado(int id, String nombre, Session sesion);
    
    public int eliminarConectado(Session sesion);
    
    public Map<Integer, UsuarioConectado> getConectados();
    
    public void registrarMensaje(int emisor, int destintario, String texto);
    
    public void notificarPost(Post p);
    
    public void notificarMeGusta(int idPost);
    
    public void notificarEliminacionMeGusta(int idPost);
    
    public void expulsarUsuario(int id);
    
}
