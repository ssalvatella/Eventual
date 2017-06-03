<%-- 
    Document   : perfil_organizacion
    Created on : 01-jun-2017, 12:18:53
    Author     : Samuel
--%>

<%@page import="com.eventual.stateless.modelo.PerfilOrganizacion"%>
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
        PerfilSocial perfil = (PerfilSocial) request.getAttribute("perfil");
        PerfilOrganizacion perfilOrg = (PerfilOrganizacion) request.getAttribute("datos");
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        
        List<Post> posts = (List<Post>) request.getAttribute("posts");

        // Obtenemos la fecha de registro en un formato "amigable"
        LocalDateTime datetime = LocalDateTime.parse(usuario.getFechaRegistro(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String fechaRegistro = datetime.format(DateTimeFormatter.ofPattern("d 'de' MMM"));
        
        String bloqueEscribirComentario = "<form class=\"form-horizontal\">"
                + "<div class=\"form-group margin-bottom-none\">"
                + "<div class=\"col-sm-9\">"
                + "<input class=\"form-control input-sm\" placeholder=\"Comentar\">"
                + "</div><div class=\"col-sm-3\">"
                + "<button type=\"submit\" class=\"btn btn-danger pull-right btn-block btn-sm\">Enviar</button></div></div></form>";
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | <% out.print(perfil.getNombre()); %></title>
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
                              <img class="profile-user-img img-responsive img-circle" src="./assets/img/bar1.jpg" alt="User profile picture">

                              <h3 class="profile-username text-center"> <% out.print(perfilOrg.getNombre()); %>
                                <small></small> </h3>

                              <p class="text-muted text-center"><% out.print(perfilOrg.getId()); %>
                                <small></small> </p>

                              <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                  <b>Amigos</b> <a class="pull-right">1,322</a>
                                </li>
                                <li class="list-group-item">
                                  <b>Organizaciones</b> <a class="pull-right">543</a>
                                </li>
                                <li class="list-group-item">
                                  <b>Eventos</b> <a class="pull-right">13,287</a>
                                </li>
                              </ul>
                                <form type ="POST" action="./PerfilUsuario">
                                    <input name="id" type="hidden" value="<% out.print(perfil.getId()); %>">
                                    <input name="usuario" type="hidden" value="<% out.print(perfilOrg.getId()); %>">
                                    <input  value="Añadir amigo" type="submit" class="btn btn-primary btn-block">
                                </form>
                            </div>
                        </div>
                        <div class="box box-primary">
                            <div class="box-header with-border">
                              <h3 class="box-title">Sobre mi</h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                              <strong><i class="fa fa-book margin-r-5"></i> Estudios</strong>

                              <p class="text-muted">
                                B.S. in Computer Science from the University of Tennessee at Knoxville
                              </p>

                              <hr>

                              <strong><i class="fa fa-map-marker margin-r-5"></i> Ubicación</strong>

                              <p class="text-muted">Malibu, California</p>

                              <hr>

                              <strong><i class="fa fa-pencil margin-r-5"></i> Habilidades</strong>

                              <p>
                                <span class="label label-danger">UI Design</span>
                                <span class="label label-success">Coding</span>
                                <span class="label label-info">Javascript</span>
                                <span class="label label-warning">PHP</span>
                                <span class="label label-primary">Node.js</span>
                              </p>

                              <hr>

                              <strong><i class="fa fa-file-text-o margin-r-5"></i> Notas</strong>

                              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam fermentum enim neque.</p>
                            </div>
                            <!-- /.box-body -->
                          </div>
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
    </body>
</html>