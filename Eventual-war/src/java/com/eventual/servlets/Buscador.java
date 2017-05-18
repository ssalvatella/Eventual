/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.stateless.modelo.PerfilSocial;
import com.eventual.stateless.modelo.PerfilSocialRemote;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/**
 *
 * @author Samuel
 */
public class Buscador extends HttpServlet {

    @EJB
    private PerfilSocialRemote ps;

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
            List<PerfilSocial> resultados = this.ps.buscar(campo);
            if (resultados.isEmpty()) return; // Si no hay salimos
            
            response.setContentType("application/json");
            // Deberemos enviar la respuesta en un JSON
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(resultados));
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
