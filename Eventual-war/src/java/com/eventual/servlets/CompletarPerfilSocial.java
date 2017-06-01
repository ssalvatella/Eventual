/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.singleton.GestorIdentificacionesLocal;
import com.eventual.stateful.SesionSocialRemote;
import com.eventual.stateless.modelo.PerfilSocialRemote;
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
public class CompletarPerfilSocial extends HttpServlet {
    
    @EJB
    private GestorIdentificacionesLocal gestorTokens;
    
    @EJB
    private PerfilSocialRemote ps;
    
    private SesionSocialRemote sesionSocial;

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
         // Obtenemos el EJB de nuestra sesión
        HttpSession sesion = request.getSession();
        this.sesionSocial = (SesionSocialRemote) sesion.getAttribute("sesionSocial");
        
         // Comprobamos el nivel del usuario y que este correctamente conectado
        if (sesionSocial.usuarioConectado() && 
                sesionSocial.esSocial() && 
                this.gestorTokens.validarToken(this.sesionSocial.getToken())) {
            request.setAttribute("perfil", this.sesionSocial.getPerfil());
            request.setAttribute("usuario", this.sesionSocial.getUsuario());
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("social/completar_perfil_social.jsp").forward(request, response);
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
        HttpSession sesion = request.getSession();
        this.sesionSocial = (SesionSocialRemote) sesion.getAttribute("sesionSocial");        
        int id = this.sesionSocial.getPerfil().getId();
        String fechaNacimiento = request.getParameter("nacimiento");
        String ciudad = request.getParameter("ciudad");
        String estudios = request.getParameter("estudios");
        String profesion = request.getParameter("profesion");
        String descripcion = request.getParameter("descripcion");
        
        this.ps.actualizar(id, fechaNacimiento, ciudad, estudios, profesion, descripcion);
        response.sendRedirect("./Social");
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
