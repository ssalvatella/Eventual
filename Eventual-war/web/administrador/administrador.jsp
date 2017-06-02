<%-- 
    Document   : administrador
    Created on : 18-may-2017, 13:20:52
    Author     : Samuel
--%>

<%@page import="com.eventual.stateless.modelo.PerfilAdministrador"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.eventual.stateless.modelo.Usuario"%>
<%@page import="com.eventual.stateless.modelo.PerfilSocial"%>
<%@page import="com.eventual.herramientas.Plantilla"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | Inicio</title>
        <% out.print(Plantilla.cargarHojasCSS()); %>
        <link href="./assets/plugins/awesomplete-gh-pages/awesomplete.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css" rel="stylesheet">
    </head>
    <%
        PerfilAdministrador perfil = (PerfilAdministrador) request.getAttribute("perfil");
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        
        int numero_mensajes = Integer.parseInt(request.getAttribute("numero_mensajes") + "");
        int numero_posts = Integer.parseInt(request.getAttribute("numero_posts") + "");
        int usuarios_nuevos = Integer.parseInt(request.getAttribute("usuarios_nuevos") + "");
        
        // Obtenemos la fecha de registro en un formato "amigable"
        LocalDateTime datetime = LocalDateTime.parse(usuario.getFechaRegistro(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String fechaRegistro = datetime.format(DateTimeFormatter.ofPattern("d 'de' MMM"));
    %>
    <body class="hold-transition skin-red sidebar-mini">
        <div class="wrapper">
                <%@ include file="cabecera.jsp" %>
                <%@ include file="menu.jsp" %>
  
            <div class="content-wrapper">              
                <!-- Cabeza de página -->
                <section class="content-header">
                  <h1>
                    Hola, <% out.print(perfil.getNombre().split(" ", 2)[0]); %>
                    <small></small>
                  </h1>
                  <ol class="breadcrumb">
                    <li><a href=""><i class="active fa fa-dashboard"></i> Inicio</a></li>
                  </ol>
                </section>
                <!-- FIN Cabeza de página -->
                <!-- Main content -->
            <section class="content">
              <div class="row">
                <div class="col-xs-6">
                  <!-- interactive chart -->
                  <div class="box box-primary">
                    <div class="box-header with-border">
                      <i class="fa fa-bar-chart-o"></i>

                      <h3 class="box-title">Usuarios conectados</h3>
                    </div>
                    <div class="box-body">
                      <div id="interactive" style="height: 300px;"></div>
                    </div>
                    <!-- /.box-body-->
                  </div>
                  <!-- /.box -->
                </div>
                <div class="col-xs-6">
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <div class="info-box bg-aqua">
                          <span class="info-box-icon"><i class="fa fa-bookmark-o"></i></span>

                          <div class="info-box-content">
                            <span class="info-box-text">Posts publicados</span>
                            <span class="info-box-number" id="numero_posts"><% out.print(numero_posts + ""); %></span>

                            <div class="progress">
                              <div class="progress-bar" style="width: 70%"></div>
                            </div>
                                <span class="progress-description">
                                  En el último día
                                </span>
                          </div>
                          <!-- /.info-box-content -->
                        </div>
                        <!-- /.info-box -->
                    </div>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <div class="info-box bg-red">
                          <span class="info-box-icon"><i class="fa fa-comments-o"></i></span>

                          <div class="info-box-content">
                            <span class="info-box-text">Mensajes de chat</span>
                            <span class="info-box-number" id="numero_mensajes"><% out.print(numero_mensajes + ""); %></span>

                            <div class="progress">
                              <div class="progress-bar" style="width: 70%"></div>
                            </div>
                                <span class="progress-description">
                                  En las últimas dos horas
                                </span>
                          </div>
                          <!-- /.info-box-content -->
                        </div>
                        <!-- /.info-box -->
                    </div>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <div class="info-box bg-green">
                          <span class="info-box-icon"><i class="ion ion-person-add"></i></span>

                          <div class="info-box-content">
                            <span class="info-box-text">Usuarios nuevos</span>
                            <span id="numero_registros" class="info-box-number"><% out.print(usuarios_nuevos + ""); %></span>

                            <div class="progress">
                              <div class="progress-bar" style="width: 70%"></div>
                            </div>
                                <span class="progress-description">
                                  En la última semana
                                </span>
                          </div>
                          <!-- /.info-box-content -->
                        </div>
                        <!-- /.info-box -->
                      </div>
                      <div class="col-md-6 col-sm-6 col-xs-12">
                        <div class="info-box bg-yellow">
                          <span class="info-box-icon"><i class="fa fa-calendar"></i></span>

                          <div class="info-box-content">
                            <span class="info-box-text">Eventos</span>
                            <span class="info-box-number">41,410</span>

                            <div class="progress">
                              <div class="progress-bar" style="width: 70%"></div>
                            </div>
                                <span class="progress-description">
                                  En la última semana
                                </span>
                          </div>
                          <!-- /.info-box-content -->
                        </div>
                        <!-- /.info-box -->
                      </div>
                
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header">
                            <i class="fa fa-users"></i>
                            <h3 class="box-title">Conectados</h3>
                        </div>
                        <div class="box-body">
                            <table id="tabla" class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                      <th>ID</th>
                                      <th>Nombre</th>
                                      <th></th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>

                </div>                          
                
                <!-- /.col -->
              </div>
              <!-- /.row -->
              <!-- /.row -->
            </section>
            <!-- /.content -->

          </div>
            <%@ include file="../pie_pagina.jsp" %>
            <%@ include file="menu_ajustes.jsp" %>
          <!-- /.content-wrapper -->
            </div>    


        </div>
        <% out.print(Plantilla.cargarJavaScripts()); %>
        <script src="./assets/plugins/awesomplete-gh-pages/awesomplete.min.js" async></script>
        <script src="./assets/js/cabecera.js" async> </script>
        <script src="./assets/plugins/flot/jquery.flot.min.js"></script>
        <script src="./assets/plugins/flot/jquery.flot.resize.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
        <script src="./assets/js/administrador.js"></script>

    </body>
</html>
