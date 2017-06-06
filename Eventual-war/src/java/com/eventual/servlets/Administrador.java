/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.stateful.SesionAdministrador;
import com.eventual.stateless.modelo.MensajeRemote;
import com.eventual.stateless.modelo.PostRemote;
import com.eventual.stateless.modelo.UsuarioRemote;
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
public class Administrador extends HttpServlet {
    
    private SesionAdministrador sesionAdministrador;
    
    @EJB
    private MensajeRemote mensajes;
    
    @EJB
    private PostRemote posts;
    
    @EJB
    private UsuarioRemote usuarios;

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
        HttpSession sesion = request.getSession(false);
        this.sesionAdministrador = (SesionAdministrador) sesion.getAttribute("sesionAdministrador");

        // Comprobamos el nivel del usuario y que este correctamente conectado
        if (sesionAdministrador.conectado()) {
            request.setAttribute("perfil", this.sesionAdministrador.getPerfil());
            request.setAttribute("usuario", this.sesionAdministrador.getUsuario());
            request.setAttribute("numero_mensajes", this.mensajes.cuentaUltimosMensajes());
            request.setAttribute("numero_posts", this.posts.cuentaUltimosPosts());
            request.setAttribute("usuarios_nuevos", this.usuarios.ultimosUsuarios());
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("./administrador/administrador.jsp").forward(request, response);
        } else { // En caso contrario redirigimos al inicio
            sesion.invalidate(); // Destruímos la sesión
            response.sendRedirect("./Inicio");
        }
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
