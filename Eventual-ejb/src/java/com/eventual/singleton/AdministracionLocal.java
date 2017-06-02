/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import com.google.gson.JsonObject;
import java.util.Map;
import javax.ejb.Local;
import javax.websocket.Session;

/**
 *
 * @author Samuel
 */
@Local
public interface AdministracionLocal {

    public Map<Integer, UsuarioConectado> getAdministradores();
    
    public UsuarioConectado devuelveConectado(int id, String nombre, Session sesion);
    
    public void añadirConectado(UsuarioConectado usuario);
    
    public void notificarNumeroUsuarios();
    
    public void notificarNuevoUsuario(UsuarioConectado u);
    
    public void notificarDesconexionUsuario(int desconectado);
    
    public JsonObject devuelveConectados();
    
    public int eliminarConectado(Session sesion);
    
    public void notificarNumeroMensajes();
    
    public void notificarNumeroPosts();

}