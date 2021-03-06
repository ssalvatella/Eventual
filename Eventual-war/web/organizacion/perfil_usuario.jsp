<%-- 
    Document   : perfilUsuario
    Created on : 16-may-2017, 15:53:38
    Author     : Pablo
--%>

<%@page import="com.eventual.stateless.modelo.PerfilOrganizacion"%>
<%@page import="com.eventual.stateless.modelo.PerfilAdministrador"%>
<%@page import="com.eventual.stateless.modelo.Post"%>
<%@page import="java.util.List"%>
<%@page import="com.eventual.herramientas.Plantilla"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.eventual.stateless.modelo.Usuario"%>
<%@page import="com.eventual.stateless.modelo.PerfilSocial"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <%
        PerfilOrganizacion perfil = (PerfilOrganizacion) request.getAttribute("perfil");
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        
        List<Post> posts = (List<Post>) request.getAttribute("posts");
        
        String bloqueEscribirComentario = "<form class=\"form-horizontal\">"
                + "<div class=\"form-group margin-bottom-none\">"
                + "<div class=\"col-sm-9\">"
                + "<input class=\"form-control input-sm\" placeholder=\"Comentar\">"
                + "</div><div class=\"col-sm-3\">"
                + "<button type=\"submit\" class=\"btn btn-danger pull-right btn-block btn-sm\">Enviar</button></div></div></form>";
        
        PerfilSocial usuarioSocial = (PerfilSocial) request.getAttribute("datos");
        
        boolean sonAmigos = (Boolean) request.getAttribute("sonAmigos");
        
        // Obtenemos la fecha de registro en un formato "amigable"
        LocalDateTime datetime = LocalDateTime.parse(usuario.getFechaRegistro(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String fechaRegistro = datetime.format(DateTimeFormatter.ofPattern("d 'de' MMM"));
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | <% out.print(usuarioSocial.getNombre()); %></title>
        <% out.print(Plantilla.cargarHojasCSS()); %>
        <link href="./assets/plugins/bootstrap3-wysiwyg-master/dist/bootstrap3-wysihtml5.min.css" rel="stylesheet">
        <link href="./assets/plugins/awesomplete-gh-pages/awesomplete.css" rel="stylesheet">
    </head>

    <body class="hold-transition skin-red sidebar-mini">
        <div class="wrapper">
                <%@ include file="cabecera.jsp" %>
                <%@ include file="menu.jsp" %>
  
           
            <div class="content-wrapper">
              
                <section class="content-header">
                    <h1>
                      Perfil de usuario
                    </h1>
                    <ol class="breadcrumb">
                      <li><a href="#"><i class="fa fa-home"></i> Inicio</a></li>
                      <li><a href="#">Perfil de usuario</a></li>
                    </ol>
                  </section>
                
                <section class="content">
                <div class="row">
                    <div class="col-md-3">
                        <div class="box box-primary">
                            <div class="box-body box-profile">
                              <img class="profile-user-img img-responsive img-circle" src="./assets/plugins/admin-lte/img/avatar5.png" alt="User profile picture">

                              <h3 class="profile-username text-center"> <% out.print(usuarioSocial.getNombre().split(" ", 2)[0]); %>
                                <small></small> </h3>

                              <p class="text-muted text-center"><% out.print((usuarioSocial.getProfesion() != null) ? usuarioSocial.getProfesion() : ""); %>
                                <small></small> </p>

                              <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                  <b>Amigos</b> <a class="pull-right"><% out.print(usuarioSocial.getNumeroAmigos()); %></a>
                                </li>
                                <li class="list-group-item">
                                  <b>Organizaciones</b> <a class="pull-right">543</a>
                                </li>
                                <li class="list-group-item">
                                  <b>Eventos</b> <a class="pull-right">13,287</a>
                                </li>
                              </ul>
                                <form method ="POST" action="./PerfilUsuario">
                                    <input name="id" type="hidden" value="<% out.print(perfil.getId()); %>">
                                    <input name="perfil" type="hidden" value="<% out.print(usuarioSocial.getId()); %>">
                                    <input  value="<% out.print((sonAmigos) ? "Eliminar amigo" : "Añadir amigo"); %>" type="submit" class="btn <% out.print((sonAmigos) ? "btn-danger" : "btn-primary"); %> btn-block">
                                </form>
                            </div>
                        </div>
                        <div class="box box-primary">
                            <div class="box-header with-border">
                              <h3 class="box-title">Sobre mí</h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                              <strong><i class="fa fa-book margin-r-5"></i> Estudios</strong>

                              <p class="text-muted">
                                <% out.print((usuarioSocial.getEstudios() != null) ? usuarioSocial.getEstudios() : ""); %>
                              </p>

                              <hr>

                              <strong><i class="fa fa-map-marker margin-r-5"></i> Ubicación</strong>

                              <p class="text-muted"><% out.print((usuarioSocial.getCiudad() != null) ? usuarioSocial.getCiudad() : ""); %></p>

                              <hr>

                              <strong><i class="fa fa-file-text-o margin-r-5"></i> Notas</strong>
                              <p><% out.print((usuarioSocial.getDescripcion() != null) ? usuarioSocial.getDescripcion() : ""); %></p>
                            </div>
                            <!-- /.box-body -->
                          </div>
                        <%@ include file="chat.jsp" %>
                    </div>
                    <div class="col-md-9">     
                        <div class="box box-warning">
                            <div class="box-header with-border">
                              <h3 class="box-title"></h3>

                              <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                              </div>
                              <!-- /.box-tools -->
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body" id="cuadro_posts">
                                
                                <%
                                    for (Post p : posts) {
                                        out.print("<div class=\"post\" id=\"" + p.getIdPost() + "\"> ");
                                        out.print("<div class=\"user-block\">");
                                        out.print("<img class=\"img-circle img-bordered-sm\" src=\"./assets/plugins/admin-lte/img/avatar5.png\" alt=\"User Image\">");
                                        out.print("<span class=\"username\">");
                                        out.print("<a href=\"#\">" + p.getNombreUsuario() + "</a>");
                                        out.print("");
                                        out.print("</span>");
                                        out.print("<span class=\"description\">" + p.getFecha() + "</span>");
                                        out.print("</div>");
                                        out.print(p.getContenido());
                                        String comentarios = (p.getNumero_comentarios() == 0)? "Comentar":"Comentarios (" + p.getNumero_comentarios() + ")";
                                        String megustas = (p.getNumero_me_gustas() == 0)? "":" (" + p.getNumero_me_gustas() + ")";
                                        String meGustaPulsado = (p.isMeGustaPulsado()) ? "text-green" : "";
                                        out.print("<ul class=\"list-inline\">"
                                                + "<li><a href=\"#\" class=\"link-black text-sm\"><i class=\"fa fa-share margin-r-5\"></i> Compartir</a></li><li><span style=\"cursor:pointer\" class=\"link-black text-sm meGusta "+ meGustaPulsado +"\" value=\"" + p.getNumero_me_gustas() + "\"><i class=\"fa fa-thumbs-o-up margin-r-5\"></i> Me gusta"+ megustas +"</span></li><li class=\"pull-right\"><a href=\"#\" class=\"link-black text-sm\"><i class=\"fa fa-comments-o margin-r-5\"></i> " + comentarios + " </a></li>"
                                                + "</ul>");
                                        out.print(bloqueEscribirComentario);
                                        out.print("</div>");
                                    }
                                %>
                            </div>
                            <!-- /.box-body -->
                        </div>
                    </div>
                </div>
                </section>
            </div>    
                <%@ include file="../pie_pagina.jsp" %>
                <%@ include file="menu_ajustes.jsp" %>

        </div>
        <% out.print(Plantilla.cargarJavaScripts()); %>
        <script src="./assets/plugins/bootstrap3-wysiwyg-master/dist/bootstrap3-wysihtml5.all.min.js"> </script>
        <script src="./assets/plugins/awesomplete-gh-pages/awesomplete.min.js" async></script>
        <script src="./assets/js/cabecera.js" async> </script>
        <script src="./assets/js/chat.js" async> </script>
        <script src="./assets/js/posts.js" async> </script>
    </body>
</html>