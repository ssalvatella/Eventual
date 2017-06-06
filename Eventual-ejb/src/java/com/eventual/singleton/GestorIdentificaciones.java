/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.singleton;

import java.util.Date;
import java.util.HashMap;
import javax.ejb.Singleton;

/**
 *
 * @author Samuel
 */
@Singleton
public class GestorIdentificaciones implements GestorIdentificacionesLocal {
    
    // Los Tokens caducan despues de 2 horas
    private final int CADUCIDAD_TOKENS = 2;

    private HashMap<String, Date> identificados = new HashMap<>();

    @Override
    public void registrarToken(String token) {
        this.identificados.put(token, new Date());
    }

    @Override
    public boolean validarToken(String token) {
        Date fechaToken = this.identificados.get(token);
        if (fechaToken == null) {
            return false;
        } else {
            Date ahora = new Date();
            int diferenciaHoras = (int) ((ahora.getTime() - fechaToken.getTime()) / (1000 * 60 * 60));
            if (diferenciaHoras <= CADUCIDAD_TOKENS) {
                return true;
            } else {
                this.identificados.remove(token);
                return false;
            }
        }
    }
    
}
