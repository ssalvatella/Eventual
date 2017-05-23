/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.stateless.modelo.PerfilOrganizacion;
import com.eventual.stateless.modelo.PerfilOrganizacionRemote;
import com.eventual.stateless.modelo.PerfilSocial;
import com.eventual.stateless.modelo.PerfilSocialRemote;
import com.eventual.stateless.modelo.Usuario;
import com.eventual.stateless.modelo.UsuarioRemote;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pablo
 */
public class PerfilUsuario extends HttpServlet {
    
    @EJB
    private PerfilSocialRemote social;
    
    @EJB
    private PerfilOrganizacionRemote organizacion;
    
    @EJB
    private UsuarioRemote usuario;
    
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
        // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
        request.getRequestDispatcher("/perfilUsuario.jsp").forward(request, response);
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
            
        boolean idIndicado = request.getParameter("perfil") != null;

        if(idIndicado){
            try{
                int id = Integer.parseInt(request.getParameter("perfil"));
                Usuario usr = this.usuario.devuelveUsuario(id);
                switch (usr.getTipo()) {
                    case SOCIAL:
                       PerfilSocial perfilUsuario = this.social.devuelve(id);
                       if(perfilUsuario != null){
                           request.setAttribute("datos", perfilUsuario);
                           redirigir(request, response, "./social/perfilUsuario.jsp");
                       }
                    break;
                    case ORGANIZACIÓN:
                       PerfilOrganizacion perfilOrganizacion = this.organizacion.devuelve(id);
                       if(perfilOrganizacion != null){
                           request.setAttribute("datos", perfilOrganizacion);
                           redirigir(request, response, "./organizacion/perfilOrganizacion.jsp");
                       }
                    break;
                    default: 
                        response.sendRedirect("./Inicio");
                    break;
                }
            }
            catch (IOException | NumberFormatException | ServletException e){
            }
        } else{
            response.sendRedirect("./Inicio");
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

    
    private void redirigir(HttpServletRequest request, HttpServletResponse response, String destino) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
        request.getRequestDispatcher(destino).forward(request, response);        
    }
    
}

   