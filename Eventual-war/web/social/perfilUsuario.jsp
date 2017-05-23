<%-- 
    Document   : perfilUsuario
    Created on : 16-may-2017, 15:53:38
    Author     : Pablo
--%>

<%@page import="com.eventual.herramientas.Plantilla"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.eventual.stateless.modelo.Usuario"%>
<%@page import="com.eventual.stateless.modelo.PerfilSocial"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <%
        PerfilSocial perfil = (PerfilSocial) request.getAttribute("datos");
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        
        // Obtenemos la fecha de registro en un formato "amigable"
        LocalDateTime datetime = LocalDateTime.parse(usuario.getFechaRegistro(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String fechaRegistro = datetime.format(DateTimeFormatter.ofPattern("d 'de' MMM"));
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
                <div class="box-body box-profile">
                  <img class="profile-user-img img-responsive img-circle" src="../../dist/img/user4-128x128.jpg" alt="User profile picture">

                  <h3 class="profile-username text-center"> Nombre : <% out.print(perfil.getNombre().split(" ", 2)[0]); %>
                    <small></small> </h3>

                  <p class="text-muted text-center"> Email : <% out.print(usuario.getEmail().split(" ", 2)[0]); %>
                    <small></small> </p>

                  <ul class="list-group list-group-unbordered">
                    <li class="list-group-item">
                      <b>Followers</b> <a class="pull-right">1,322</a>
                    </li>
                    <li class="list-group-item">
                      <b>Following</b> <a class="pull-right">543</a>
                    </li>
                    <li class="list-group-item">
                      <b>Friends</b> <a class="pull-right">13,287</a>
                    </li>
                  </ul>

                  <a href="#" class="btn btn-primary btn-block"><b>Añadir amigo</b></a>
                </div>
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