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
                        <!-- CUADRO DE CREACIÓN DE POSTS -->
                        <div class="box box-danger">
                            <div class="box-header with-border">
                                <div class="box-body">
                                    <form method="post" action="./Social" id="publicador_posts" style="padding-top: 15px"></form>
                                    <textarea name="contenido" style="width: 100%; height: 100px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;" id="texto_post" placeholder="¿Qué estas pensando?"></textarea>
                                        <input style="display: none" name="usuario" value="<% out.write(perfil.getId() + ""); %>"/>
                                        <div class="form-group margin-bottom-none">
                                          <div class="col-sm-9">
                                          </div>
                                          <div class="col-sm-3">
                                            <button style="margin-top: 15px; margin-left: 140px" id="boton_publicar" type="" class="btn btn-danger pull-right btn-block btn-sm">Publicar</button>
                                          </div>
                                        </div>
                                      </form>
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
                      <%@ include file="chat.jsp" %>     
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
        <script>
            
            $(document).on('click','#boton_publicar',function(){
                $.ajax({
                    type: "POST",
                    url: "./Social",
                    data: {
                        'usuario' : $('#ID_USUARIO').text(),
                        'contenido' : $('#texto_post').val()
                    },
                    success: function() {
                          window.location.href = "./Social";
                    },
                    error: function(xhr, ajaxOptions, thrownError) {
                         alert(xhr.responseText);
                    }
                });
             });
            
        </script>
    </body>
</html>
