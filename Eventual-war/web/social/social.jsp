<%-- 
    Document   : social
    Created on : 09-may-2017, 16:40:28
    Author     : Samuel
--%>

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
        <link href="./assets/plugins/bootstrap3-wysiwyg-master/dist/bootstrap3-wysihtml5.min.css" rel="stylesheet">
        <link href="./assets/plugins/awesomplete-gh-pages/awesomplete.css" rel="stylesheet">
    </head>
    <%
        PerfilSocial perfil = (PerfilSocial) request.getAttribute("perfil");
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        
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
                    <li><a href=""><i class="active fa fa-home"></i> Inicio</a></li>
                  </ol>
                </section>
                <!-- FIN Cabeza de página -->
                
                <section class="content">
                
                <div class="row">
                    <div class="col-md-8">
                        <div class="box box-danger">
                            <div class="box-header with-border">
                                <div class="box-body">
                                    <textarea style="width: 100%; height: 100px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;" id="texto_post" placeholder="¿Qué estas pensando?"></textarea>
                                </div>
                            </div>
                        </div>
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
                            <div class="box-body">
                            <div class="post clearfix">
                                  <div class="user-block">
                                    <img class="img-circle img-bordered-sm" src="./assets/plugins/admin-lte/img/avatar5.png" alt="User Image">
                                        <span class="username">
                                          <a href="#">Sarah Ross</a>
                                          <a href="#" class="pull-right btn-box-tool"><i class="fa fa-times"></i></a>
                                        </span>
                                    <span class="description">Compartió - 3 days ago</span>
                                  </div>
                                  <!-- /.user-block -->
                                  <p>
                                    Lorem ipsum represents a long-held tradition for designers,
                                    typographers and the like. Some people hate it and argue for
                                    its demise, but others ignore the hate as they create awesome
                                    tools to help create filler text for everyone from bacon lovers
                                    to Charlie Sheen fans.
                                  </p>

                                  <form class="form-horizontal">
                                    <div class="form-group margin-bottom-none">
                                      <div class="col-sm-9">
                                        <input class="form-control input-sm" placeholder="Response">
                                      </div>
                                      <div class="col-sm-3">
                                        <button type="submit" class="btn btn-danger pull-right btn-block btn-sm">Enviar</button>
                                      </div>
                                    </div>
                                  </form>
                                </div>
                            </div>
                            <!-- /.box-body -->
                        </div>
                    </div>
                    <div class="col-md-4">
                        <!-- CHAT -->
                        <div class="box box-danger direct-chat direct-chat-danger">
                            <div id="cargando" class="overlay"><i class="fa fa-refresh fa-spin"></i></div>
                            <div class="box-header with-border">
                              <h3 class="box-title">Chat</h3>
                              <div class="box-tools pull-right">
                                <span data-toggle="tooltip" title="" class="badge bg-red" data-original-title="3 nuevos mensajes">3</span>
                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                <button class="btn btn-box-tool" data-toggle="tooltip" title="" data-widget="chat-pane-toggle" data-original-title="Contactos"><i class="fa fa-comments"></i></button>
                                <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                              </div>
                            </div><!-- /.box-header -->
                            <div class="box-body">
                              <!-- Conversations are loaded here -->
                              <div class="direct-chat-messages">
                                <!-- Message. Default to the left -->
                                <div class="direct-chat-msg">
                                  <div class="direct-chat-info clearfix">
                                    <span class="direct-chat-name pull-left">Alexander Pierce</span>
                                    <span class="direct-chat-timestamp pull-right">23 Jan 2:00 pm</span>
                                  </div><!-- /.direct-chat-info -->
                                  <img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><!-- /.direct-chat-img -->
                                  <div class="direct-chat-text">
                                    Prueba de mensaje.
                                  </div><!-- /.direct-chat-text -->
                                </div><!-- /.direct-chat-msg -->

                                <!-- Message to the right -->
                                <div class="direct-chat-msg right">
                                  <div class="direct-chat-info clearfix">
                                    <span class="direct-chat-name pull-right"><% out.print(perfil.getNombre()); %></span>
                                    <span class="direct-chat-timestamp pull-left">23 Jan 2:05 pm</span>
                                  </div><!-- /.direct-chat-info -->
                                  <img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><!-- /.direct-chat-img -->
                                  <div class="direct-chat-text">
                                    Esto es una respuesta de prueba.
                                  </div><!-- /.direct-chat-text -->
                                </div><!-- /.direct-chat-msg -->
                              </div><!--/.direct-chat-messages-->

                              <!-- Contacts are loaded here -->
                              <div class="direct-chat-contacts">
                                <ul id="lista_amigos" class="contacts-list">
                                  <li>
                                    <a href="#">
                                      <img class="contacts-list-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="Contact Avatar">
                                      <div class="contacts-list-info">
                                        <span class="contacts-list-name">
                                          Count Dracula
                                          <small class="contacts-list-date pull-right">2/28/2015</small>
                                        </span>
                                        <span class="contacts-list-msg">How have you been? I was...</span>
                                      </div><!-- /.contacts-list-info -->
                                    </a>
                                  </li><!-- End Contact Item -->
                                </ul><!-- /.contatcts-list -->
                              </div><!-- /.direct-chat-pane -->
                            </div><!-- /.box-body -->
                            <div class="box-footer">
                              <form action="#" method="post">
                                <div class="input-group">
                                  <input name="message" placeholder="Escribe un mensaje..." class="form-control" type="text">
                                  <span class="input-group-btn">
                                    <button type="button" class="btn btn-danger btn-flat">Enviar</button>
                                  </span>
                                </div>
                              </form>
                            </div><!-- /.box-footer-->
                          </div>
                        <!-- FIN CHAT -->
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
        <script type="text/javascript">
            $('#texto_post').wysihtml5();
        </script>
    </body>
</html>
