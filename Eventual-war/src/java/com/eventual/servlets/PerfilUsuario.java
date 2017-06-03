/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.servlets;

import com.eventual.stateful.SesionAdministradorRemote;
import com.eventual.stateful.SesionOrganizacionRemote;
import com.eventual.stateful.SesionSocialRemote;
import com.eventual.stateless.modelo.PerfilOrganizacion;
import com.eventual.stateless.modelo.PerfilOrganizacionRemote;
import com.eventual.stateless.modelo.PerfilSocial;
import com.eventual.stateless.modelo.PerfilSocialRemote;
import com.eventual.stateless.modelo.PostRemote;
import com.eventual.stateless.modelo.Usuario;
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
 * @author Pablo
 */
public class PerfilUsuario extends HttpServlet {
    
    private SesionSocialRemote sesionSocial;
    
    private SesionAdministradorRemote sesionAdministrador;
    
    private SesionOrganizacionRemote sesionOrganizacion;
    
    @EJB
    private PerfilSocialRemote social;
    
    @EJB
    private PerfilOrganizacionRemote organizacion;
    
    @EJB
    private UsuarioRemote usuario;
    
    @EJB
    private PostRemote posts;
    
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
            
        boolean idIndicado = request.getParameter("perfil") != null;

        if(idIndicado){
            try {               
                // Obtenemos el EJB de nuestra sesión
                HttpSession sesion = request.getSession();
                Usuario usuario = (Usuario) sesion.getAttribute("usuario");
                int id = Integer.parseInt(request.getParameter("perfil"));
                Usuario usr = this.usuario.devuelveUsuario(id);
                switch (usuario.getTipo()) {
                    case SOCIAL:
                        this.sesionSocial = (SesionSocialRemote) sesion.getAttribute("sesionSocial");
                        request.setAttribute("perfil", this.sesionSocial.getPerfil());
                        request.setAttribute("usuario", this.sesionSocial.getUsuario());
                        request.setAttribute("posts", this.posts.devuelvePostsUsuario(this.sesionSocial.getPerfil().getId(), id));
                        request.setAttribute("sonAmigos", this.usuario.sonAmigos(this.sesionSocial.getPerfil().getId(), id));
                        switch (usr.getTipo()) {
                            case SOCIAL:
                               PerfilSocial perfilUsuario = this.social.devuelve(id);
                               if(perfilUsuario != null){
                                    request.setAttribute("datos", perfilUsuario);
                                    response.setContentType("text/html;charset=UTF-8");
                                    // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
                                    request.getRequestDispatcher("/social/perfil_usuario.jsp").forward(request, response);
                               }
                            break;
                            case ORGANIZACIÓN:
                               PerfilOrganizacion perfilOrganizacion = this.organizacion.devuelve(id);
                               if(perfilOrganizacion != null){
                                   request.setAttribute("datos", perfilOrganizacion);
                                   response.setContentType("text/html;charset=UTF-8");
                                   // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
                                   request.getRequestDispatcher("/social/perfil_organizacion.jsp").forward(request, response);
                               }
                            break;
                        }                        
                    break;
                    case ADMINISTRADOR:
                        this.sesionAdministrador = (SesionAdministradorRemote) sesion.getAttribute("sesionAdministrador");
                        request.setAttribute("perfil", this.sesionAdministrador.getPerfil());
                        request.setAttribute("usuario", this.sesionAdministrador.getUsuario());
                        request.setAttribute("posts", this.posts.devuelvePostsUsuario(this.sesionAdministrador.getPerfil().getId(), id));
                        request.setAttribute("sonAmigos", this.usuario.sonAmigos(this.sesionAdministrador.getPerfil().getId(), id));
                        switch (usr.getTipo()) {
                            case SOCIAL:
                               PerfilSocial perfilUsuario = this.social.devuelve(id);
                               if(perfilUsuario != null){
                                    request.setAttribute("datos", perfilUsuario);
                                    response.setContentType("text/html;charset=UTF-8");
                                    // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
                                    request.getRequestDispatcher("/administrador/perfil_usuario.jsp").forward(request, response);
                               }
                            break;
                            case ORGANIZACIÓN:
                               PerfilOrganizacion perfilOrganizacion = this.organizacion.devuelve(id);
                               if(perfilOrganizacion != null){
                                   request.setAttribute("datos", perfilOrganizacion);
                                   response.setContentType("text/html;charset=UTF-8");
                                   // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
                                   request.getRequestDispatcher("/administrador/perfil_organizacion.jsp").forward(request, response);
                               }
                            break;
                        }                        
                    break;
                    case ORGANIZACIÓN:
                        this.sesionOrganizacion = (SesionOrganizacionRemote) sesion.getAttribute("sesionOrganizacion");
                        request.setAttribute("perfil", this.sesionOrganizacion.getPerfil());
                        request.setAttribute("usuario", this.sesionOrganizacion.getUsuario());
                        request.setAttribute("posts", this.posts.devuelvePostsUsuario(this.sesionOrganizacion.getPerfil().getId(), id));
                        request.setAttribute("sonAmigos", this.usuario.sonAmigos(this.sesionOrganizacion.getPerfil().getId(), id));
                        switch (usr.getTipo()) {
                            case SOCIAL:
                               PerfilSocial perfilUsuario = this.social.devuelve(id);
                               if(perfilUsuario != null){
                                    request.setAttribute("datos", perfilUsuario);
                                    response.setContentType("text/html;charset=UTF-8");
                                    // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
                                    request.getRequestDispatcher("/organizacion/perfil_usuario.jsp").forward(request, response);
                               }
                            break;
                            case ORGANIZACIÓN:
                               PerfilOrganizacion perfilOrganizacion = this.organizacion.devuelve(id);
                               if(perfilOrganizacion != null){
                                   request.setAttribute("datos", perfilOrganizacion);
                                   response.setContentType("text/html;charset=UTF-8");
                                   // Devolvemos como respuesta el .JSP para cargar la pantalla del perfil del usuario
                                   request.getRequestDispatcher("/organizacion/perfil_organizacion.jsp").forward(request, response);
                               }
                            break;
                        }                         
                    break;
                }
            }
            catch (IOException | NumberFormatException | ServletException e){
            }
        } else{
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
        
        String paramId = request.getParameter("id");
        String paramUsuario = request.getParameter("perfil");
        
        if (paramId != null && paramUsuario != null) {
            int id = Integer.parseInt(paramId);
            int usuario = Integer.parseInt(paramUsuario);      
            this.usuario.registrarAmistad(id, usuario);
        }    
        
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

   