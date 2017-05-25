/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.stateless.modelo.Perfil;
import com.eventual.stateless.modelo.PerfilOrganizacionRemote;
import com.eventual.stateless.modelo.PerfilSocialRemote;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samuel
 */
public class Buscador extends HttpServlet {

    @EJB
    private PerfilSocialRemote ps;
    
    @EJB
    private PerfilOrganizacionRemote po;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String peticion = request.getParameter("peticion");
        String campo = request.getParameter("campo");
        if (peticion != null && campo != null) {
            
            // Obtenemos los resultados de la búsqueda
            List<Perfil> usuarios = this.ps.buscar(campo);
            List<Perfil> organizaciones = this.po.buscar(campo);
            if (usuarios.isEmpty() && organizaciones.isEmpty()) return; // Si no hay salimos
            usuarios.addAll(organizaciones);
            response.setContentType("application/json");
            // Deberemos enviar la respuesta en un JSON
            Gson gson = new Gson();
            
            response.getWriter().write(gson.toJson(usuarios));

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
