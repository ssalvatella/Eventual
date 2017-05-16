/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import javax.websocket.Session;

/**
 *
 * @author Samuel
 */
    public class UsuarioConectado {
        
        private final int idUsuario;
        private final String nombre;
        private final int[] amigos;
        private final Session sesion;
        
        public UsuarioConectado(int idUsuario, String nombre, int[] amigos, Session sesion) {
            this.idUsuario = idUsuario;
            this.nombre = nombre;
            this.amigos = amigos;
            this.sesion = sesion;
        }

        public int getIdUsuario() {
            return idUsuario;
        }

        public String getNombre() {
            return nombre;
        }

        public int[] getAmigos() {
            return amigos;
        }

        public Session getSesion() {
            return sesion;
        }
        
    }