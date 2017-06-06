/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.singleton.GestorIdentificacionesLocal;
import com.eventual.stateful.SesionOrganizacion;
import com.eventual.stateless.modelo.Evento;
import com.eventual.stateless.modelo.EventoRemote;
import com.eventual.stateless.modelo.Post;
import com.eventual.stateless.modelo.PostRemote;
import java.io.IOException;
import java.util.List;
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
public class Organizacion extends HttpServlet {
    
    @EJB
    private PostRemote posts;
    
    @EJB
    private EventoRemote evento;

    private SesionOrganizacion sesionOrganizacion;
    
    @EJB
    private GestorIdentificacionesLocal gestorTokens;
    
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
        
        // Obtenemos el EJB de nuestra sesión
        HttpSession sesion = request.getSession();
        this.sesionOrganizacion = (SesionOrganizacion) sesion.getAttribute("sesionOrganizacion");
        // Comprobamos el nivel del usuario y que este correctamente conectado
        if (sesionOrganizacion.conectado() && 
                this.gestorTokens.validarToken(this.sesionOrganizacion.getToken())) {
            request.setAttribute("perfil", this.sesionOrganizacion.getPerfil());
            request.setAttribute("usuario", this.sesionOrganizacion.getUsuario());
            List<Post> posts = this.posts.devuelve(this.sesionOrganizacion.getUsuario().getId(), 10, 0);
            request.setAttribute("posts", posts);
            List<Evento> eventos = this.evento.devuelveEventosOrg(this.sesionOrganizacion.getUsuario().getId());
            request.setAttribute("eventos", eventos);
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("organizacion/organizacion.jsp").forward(request, response);
        } else { // En caso contrario redirigimos al inicio
            sesion.invalidate(); // Destruímos la sesión
            response.sendRedirect("./Inicio");
        }
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String fecha = request.getParameter("fecha");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        if (fecha != null && nombre != null && descripcion != null) {
            HttpSession sesion = request.getSession();
            this.sesionOrganizacion = (SesionOrganizacion) sesion.getAttribute("sesionOrganizacion");
            int id_organizacion = this.sesionOrganizacion.getPerfil().getId();
            this.evento.registrar(id_organizacion, fecha, nombre, descripcion);
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
