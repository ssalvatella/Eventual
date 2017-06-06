/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.stateful.SesionOrganizacion;
import com.eventual.stateful.SesionSocial;
import com.eventual.stateless.modelo.EventoRemote;
import com.eventual.stateless.modelo.PerfilOrganizacion;
import com.eventual.stateless.modelo.PerfilOrganizacionRemote;
import com.eventual.stateless.modelo.Usuario;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samuel
 */
public class Evento extends HttpServlet {
    
    private SesionSocial sesionSocial;    
    
    private SesionOrganizacion sesionOrganizacion;
    
    @EJB
    private EventoRemote evento;
    
    @EJB
    private PerfilOrganizacionRemote organizaciones;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        response.setContentType("text/html;charset=UTF-8");
        String idEvento = request.getParameter("id");
        if (idEvento != null) {
            int id = Integer.parseInt(idEvento);
             com.eventual.stateless.modelo.Evento e = this.evento.devuelve(id);
             if (e != null) {
                PerfilOrganizacion organizador = this.organizaciones.devuelve(e.getOrganizacion());
                // Obtenemos el EJB de nuestra sesión
                HttpSession sesion = request.getSession();
                Usuario usuario = (Usuario) sesion.getAttribute("usuario");
                switch (usuario.getTipo()) {
                    case ORGANIZACIÓN:
                        this.sesionOrganizacion = (SesionOrganizacion) sesion.getAttribute("sesionOrganizacion");
                        request.setAttribute("perfil", this.sesionOrganizacion.getPerfil());
                        request.setAttribute("usuario", this.sesionOrganizacion.getUsuario());
                        request.setAttribute("evento", e);
                        request.setAttribute("organizador", organizador);
                        request.getRequestDispatcher("/organizacion/evento.jsp").forward(request, response);
                    break;
                    case SOCIAL:
                        this.sesionSocial = (SesionSocial) sesion.getAttribute("sesionSocial");
                        request.setAttribute("perfil", this.sesionSocial.getPerfil());
                        request.setAttribute("usuario", this.sesionSocial.getUsuario());
                        request.setAttribute("evento", e);
                        request.setAttribute("organizador", organizador);
                        request.getRequestDispatcher("/social/evento.jsp").forward(request, response);
                    break;
                }

             }
        }

    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        processRequest(request, response);
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
