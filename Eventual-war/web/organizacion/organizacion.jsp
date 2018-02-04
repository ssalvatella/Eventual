<%-- 
    Document   : organizacion
    Created on : 03-jun-2017, 11:05:25
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
        
        List<Post> posts = (List<Post>) request.getAttribute("posts");
        List<Evento> eventos = (List<Evento>) request.getAttribute("eventos");
        
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
                    Hola, <% out.print(perfil.getNombre()); %>
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
                                    <textarea class="texto_html" name="contenido" style="width: 100%; height: 150px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;" id="texto_post" placeholder="¿Qué estas pensando?"></textarea>
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
                            <div class="box-body" id="cuadro_posts">
                                
                                <%
                                    for (Post p : posts) {
                                        out.print("<div class=\"post\" id=\"" + p.getIdPost() + "\"> ");
                                        out.print("<div class=\"user-block\">");
                                        out.print("<img class=\"img-circle img-bordered-sm\" src=\"./assets/plugins/admin-lte/img/avatar5.png\" alt=\"User Image\">");
                                        out.print("<span class=\"username\">");
                                        out.print("<a href=\"./PerfilUsuario?perfil="+ p.getIdUsuario() +"\">" + p.getNombreUsuario() + "</a>");
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
                    <div class="col-md-4" >
                     <div class="affix" style="width: 27%; height: 40%">
                      <%@ include file="chat.jsp" %>
                      <div class="box">
                          <div class="box-body">
                              <a class="btn btn-lg btn-primary btn-block" data-toggle="modal" data-target="#crear_evento">Crear Evento</a>
                          </div>
                      </div>
                    <div class="box">
                        <div class="box-header">
                          <h3 class="box-title">Mis eventos</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body no-padding">
                          <table class="table table-striped">
                            <tr>
                              <th style="width: 10px">#</th>
                              <th>Nombre</th>
                              <th>Fecha</th>
                              <th>Invitados</th>
                              <th></th>
                            </tr>
                            <%
                                for (Evento e : eventos) {
                                    if (e.getNombre() != null)
                                    out.print("<tr><td>" + e.getId() + "</td><td>" + e.getNombre() + "</td><td>"+ e.getFecha() +"</td><td><span class=\"badge bg-green\">"+ e.getInvitados() +"</span></td><td><a href=\"./Evento?id=" + e.getId() + "\" class=\"btn btn-default\">Ver</a></td></tr>");
                                }

                                %>
                          </table>
                        </div>
                        <!-- /.box-body -->
                      </div>
                     </div>
                    </div>
                </div>
                </section>
            </div>    
                <%@ include file="../pie_pagina.jsp" %>
                <%@ include file="menu_ajustes.jsp" %>

        <div class="modal fade" id="crear_evento">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                  
                <h4 class="modal-title"><i class="fa fa-calendar-plus-o"></i> Crear Evento</h4>
              </div>
            <form method="POST" action="./Organizacion">
              <div class="modal-body">
                  
                    <div id="form_fecha" class="form-group has-feedback">
                      <label>Fecha</label>
                      <input id="input_fecha" name="fecha" type="text" class="form-control" placeholder="¿Cúando?" required>
                      <span class="glyphicon glyphicon-calendar form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                      <label>Título del evento</label>
                      <input class="form-control" name="nombre" id="input_nombre" placeholder="¿Cómo se llama el evento?" type="text" required>
                      <span  class="fa fa-glass form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                      <label>Descripción</label>
                      <textarea class="texto_html" name="descripcion" style="width: 100%; height: 300px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;" id="descripcion_evento" placeholder="Cuenta en que consistirá el evento" required></textarea>
                      <span  class="fa fa-sticky-note form-control-feedback"></span>
                    </div>

              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cerrar</button>
                <button type="submit" class="btn btn-primary">Crear</button>
              </div>
                </form>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        </div>
        <% out.print(Plantilla.cargarJavaScripts()); %>
        <script src="./assets/plugins/bootstrap3-wysiwyg-master/dist/bootstrap3-wysihtml5.all.min.js"> </script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.es.min.js"></script>
        <script src="./assets/plugins/awesomplete-gh-pages/awesomplete.min.js" async></script>
        <script src="./assets/js/cabecera.js" async> </script>
        <script src="./assets/js/chat.js" async> </script>
        <script src="./assets/js/posts.js" async> </script>
        <script type="text/javascript">
            $('.texto_html').wysihtml5();
            $('#input_fecha').datepicker({
                    language: 'es',
                    format: 'dd/mm/yyyy',
                    autoclose: true
            });
        </script>
        
    </body>
</html>