/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.stateful.SesionAdministradorRemote;
import com.eventual.stateful.SesionSocialRemote;
import com.eventual.stateless.modelo.Usuario;
import com.eventual.stateless.modelo.UsuarioRemote;
import com.eventual.webservices.cliente.IdentificadorWebService_Service;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
/**
 *
 * @author Samuel
 */
public class Inicio extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Eventual-war/IdentificadorWebService.wsdl")
    private IdentificadorWebService_Service service;

    @EJB
    private UsuarioRemote usuario;
    
    @EJB
    private SesionSocialRemote sesionSocial;
    
    @EJB
    private SesionAdministradorRemote sesionAdministrador;
   
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
        HttpSession sesion = request.getSession();
        sesion.invalidate(); // Destruímos la sesión
        response.setContentType("text/html;charset=UTF-8");
        // Devolvemos como respuesta el .JSP para cargar la pantalla de inicio
        request.getRequestDispatcher("/inicio.jsp").forward(request, response);
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
        // Recogemos los parámetros del formulario
        String email = request.getParameter("usuario");
        String contraseña = request.getParameter("contrasenia");
        // Procesamos la petición en caso de que esten definidos los parámetros
        if (email != null && contraseña != null) {
            try {
                HttpSession sesion = request.getSession();
                String token = validar(email, contraseña);
                if (token == null) {
                    request.setAttribute("validacion", false);
                    processRequest(request, response);                   
                } else {
                    Usuario conectado = this.usuario.devuelveUsuario(email);
                    if (conectado != null) {
                        switch (conectado.getTipo()) {
                            case SOCIAL:
                                this.sesionSocial.setToken(token);
                                this.sesionSocial.conectarUsuario(conectado);
                                // Guardamos en la sesión el EJB
                                sesion.setAttribute("sesionSocial", this.sesionSocial);
                                // Vamos al servlet "Social" para empezar la sesión
                                response.sendRedirect("./Social");   
                            break;
                            case ADMINISTRADOR:
                                this.sesionAdministrador.setToken(token);
                                this.sesionAdministrador.conectar(conectado);
                                // Guardamos en la sesión el EJB de administrador
                                sesion.setAttribute("sesionAdministrador", this.sesionAdministrador);
                                // Vamos al servlet "Administrador" para empezar la sesión
                                response.sendRedirect("./Administrador"); 
                            break;
                        }

                    } else {
                        processRequest(request, response);
                    }
                }
            } catch (Exception e) {
                
            }
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

    private String validar(java.lang.String email, java.lang.String password) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.eventual.webservices.cliente.IdentificadorWebService port = service.getIdentificadorWebServicePort();
        return port.validar(email, password);
    }

    
}
