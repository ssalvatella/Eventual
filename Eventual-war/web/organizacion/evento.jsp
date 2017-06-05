<%-- 
    Document   : evento
    Created on : 05-jun-2017, 9:34:21
    Author     : Samuel
--%>

<%@page import="com.eventual.stateless.modelo.Evento"%>
<%@page import="com.eventual.stateless.modelo.PerfilOrganizacion"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.ocpsoft.pretty.time.PrettyTime"%>
<%@page import="java.util.Locale"%>
<%@page import="com.eventual.stateless.modelo.Post"%>
<%@page import="java.util.List"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.eventual.stateless.modelo.Usuario"%>
<%@page import="com.eventual.herramientas.Plantilla"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | Inicio</title>
        <% out.print(Plantilla.cargarHojasCSS()); %>
        <link href="./assets/plugins/bootstrap3-wysiwyg-master/dist/bootstrap3-wysihtml5.min.css" rel="stylesheet">
        <link href="./assets/plugins/awesomplete-gh-pages/awesomplete.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.min.css" />
    </head>
    <%
        PerfilOrganizacion perfil = (PerfilOrganizacion) request.getAttribute("perfil");
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        
        Evento e = (Evento) request.getAttribute("evento");
        PerfilOrganizacion organizador = (PerfilOrganizacion) request.getAttribute("organizador");
        
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
    <body class="hold-transition skin-red sidebar-mini">
        <div class="wrapper">
                <%@ include file="cabecera.jsp" %>
                <%@ include file="menu.jsp" %>
  
            <div class="content-wrapper">              
                <!-- Cabeza de página -->
                <section class="content-header">
                  <h1>
                    
                    <small></small>
                  </h1>
                  <ol class="breadcrumb">
                    <li><a href="./Organizacion"><i class="fa fa-home"></i> Inicio</a></li>
                    <li>Eventos</li>
                    <li><i class="active"></i><% out.print(e.getNombre()); %></li>
                  </ol>
                </section>
                <!-- FIN Cabeza de página -->
                
                <section class="content">
                    <div class="row">
                        <div class="col-md-offset-3 col-md-6">
                        <div class="box box-widget widget-user">
                            <!-- Add the bg color to the header using any of the bg-* classes -->
                            <div class="widget-user-header bg-black" style="background: url('./assets/img/fondo-fiesta.jpg') center center;">
                              <h3 class="widget-user-username"><% out.print(e.getNombre()); %></h3>
                              <h5 class="widget-user-desc"><% out.print(organizador.getNombre()); %></h5>
                            </div>
                            <div class="widget-user-image">
                              <img class="img-circle" src="./assets/img/bar1.jpg" alt="User Avatar">
                            </div>
                            <div class="box-footer">
                              <div class="row">
                                <div class="col-sm-4 border-right">
                                  <div class="description-block">
                                    <h5 class="description-header"><% out.print(e.getFecha()); %></h5>
                                    <span class="description-text">FECHA</span>
                                  </div>
                                  <!-- /.description-block -->
                                </div>
                                <!-- /.col -->
                                <div class="col-sm-4 border-right">
                                  <div class="description-block">
                                    <h5 class="description-header"><% out.print(organizador.getCiudad()); %></h5>
                                    <span class="description-text">LUGAR</span>
                                  </div>
                                  <!-- /.description-block -->
                                </div>
                                <!-- /.col -->
                                <div class="col-sm-4">
                                  <div class="description-block">
                                    <h5 class="description-header"><% out.print(e.getInvitados()); %></h5>
                                    <span class="description-text">INVITADOS</span>
                                  </div>
                                  <!-- /.description-block -->
                                </div>
                                <!-- /.col -->
                              </div>
                              <!-- /.row -->
                            </div>
                            <div class="box-body">
                                <h3>Descripción</h3><br />
                                <% out.print(e.getDescripcion()); %>
                            </div>
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.es.min.js"></script>
        <script src="./assets/plugins/awesomplete-gh-pages/awesomplete.min.js" async></script>
        <script src="./assets/js/cabecera.js" async> </script>
        <script src="./assets/js/chat.js" async> </script>
        <script src="./assets/js/posts.js" async> </script>
        
    </body>
</html>

