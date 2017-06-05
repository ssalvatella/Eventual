/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.singleton.GestorIdentificacionesLocal;
import com.eventual.stateful.SesionOrganizacionRemote;
import com.eventual.stateless.modelo.PerfilOrganizacionRemote;
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
public class CompletarPerfilOrganizacion extends HttpServlet {
    
        
    @EJB
    private PerfilOrganizacionRemote po;
    
    @EJB
    private GestorIdentificacionesLocal gestorTokens;
    
    private SesionOrganizacionRemote sesionOrganizacion;


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
        
         // Obtenemos el EJB de nuestra sesión
        HttpSession sesion = request.getSession();
        this.sesionOrganizacion = (SesionOrganizacionRemote) sesion.getAttribute("sesionOrganizacion");
        
         // Comprobamos el nivel del usuario y que este correctamente conectado
        if (sesionOrganizacion.conectado() && 
                this.gestorTokens.validarToken(this.sesionOrganizacion.getToken())) {
            request.setAttribute("perfil", this.sesionOrganizacion.getPerfil());
            request.setAttribute("usuario", this.sesionOrganizacion.getUsuario());
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("organizacion/completar_perfil_organizacion.jsp").forward(request, response);
        } else { // En caso contrario redirigimos al inicio
            sesion.invalidate(); // Destruímos la sesión
            response.sendRedirect("./Inicio");
        }
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
        this.sesionOrganizacion = (SesionOrganizacionRemote) sesion.getAttribute("sesionOrganizacion");        
        int id = this.sesionOrganizacion.getPerfil().getId();
        String contacto = request.getParameter("contacto");
        String descripcion = request.getParameter("descripcion");
        String categoria = request.getParameter("categoria");
        this.po.actualizar(id, contacto, descripcion, categoria);
        
        response.sendRedirect("./Organizacion");
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
