<%-- 
    Document   : completar_perfil_organizacion
    Created on : 05-jun-2017, 10:40:18
    Author     : Samuel
--%>


<%@page import="com.eventual.stateless.modelo.PerfilOrganizacion"%>
<%@page import="com.eventual.herramientas.Plantilla"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.eventual.stateless.modelo.Usuario"%>
<%@page import="com.eventual.stateless.modelo.PerfilSocial"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | Inicio</title>
        <% out.print(Plantilla.cargarHojasCSS()); %>
        <link href="./assets/plugins/bootstrap3-wysiwyg-master/dist/bootstrap3-wysihtml5.min.css" rel="stylesheet">
        <link href="./assets/plugins/awesomplete-gh-pages/awesomplete.css" rel="stylesheet">
    </head>
    <%
        PerfilOrganizacion perfil = (PerfilOrganizacion) request.getAttribute("perfil");
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
                    Hola, <% out.print(perfil.getNombre()); %>
                    <small>Hemos visto que tu perfil no esta completo. ¡Añade más información sobre tí!</small>
                  </h1>
                  <ol class="breadcrumb">
                    <li><a href=""><i class="active fa fa-home"></i> Inicio</a></li>
                  </ol>
                </section>
                <!-- FIN Cabeza de página -->
                
                <section class="content">
                    <div class="row">
                        <div class="col-md-offset-2 col-md-5 animated bounceInUp">
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                  <h3 class="box-title">Perfil</h3>
                                </div>
                                <!-- /.box-header -->
                                <!-- form start -->
                                <form method="POST" action="./CompletarPerfilOrganizacion" role="form">
                                  <div class="box-body">
                                    <div class="form-group has-feedback">
                                      <label>Contacto</label>
                                      <input value="<% out.print((perfil.getContacto() != null) ? perfil.getContacto() : ""); %>" class="form-control" name="contacto" id="input_contacto" placeholder="Número de teléfono de contacto" type="tel" data-inputmask="'mask': ['999-99-99-99', '999-99-99-99']" data-mask>
                                      <span  class="fa fa-phone form-control-feedback"></span>
                                    </div>
                                    <div class="form-group has-feedback">
                                      <label>Descripción</label>
                                      <input value="<% out.print((perfil.getDescripcion() != null) ? perfil.getDescripcion() : ""); %>" class="form-control" name="descripcion" id="input_descripcion" placeholder="Describe tu organización" type="text">
                                      <span  class="fa fa-address-card form-control-feedback"></span>
                                    </div>
                                    <div class="form-group has-feedback">
                                      <label>Categoría</label>
                                        <input value="<% out.print((perfil.getCategoria() != null) ? perfil.getCategoria() : ""); %>" class="form-control" name="categoria" id="input_categoria" placeholder="Categoría de tu organización" type="text">
                                      <span  class="fa fa-list form-control-feedback"></span>
                                    </div>
                                  </div>
                                  <!-- /.box-body -->

                                  <div class="box-footer">
                                    <a href="./Organizacion" class="btn btn-default pull-left">Ir a inicio</a>
                                    <button type="submit" class="btn btn-primary pull-right">Guardar</button>
                                  </div>
                                </form>
                          </div>                            
                        </div>
                        <div class="col-md-3 animated bounceInRight">
                            <div class="small-box bg-green">
                                <div class="inner">
                                  <h3><% out.print((int) (perfil.completitudPerfil() * 100)); %><sup style="font-size: 20px">%</sup></h3>

                                  <p>Perfil completado</p>
                                </div>
                                <div class="icon">
                                  <i class="glyphicon glyphicon-ok"></i>
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
        <script src="./assets/plugins/inputmask/inputmask/inputmask.min.js"></script>
        <script src="./assets/plugins/inputmask/inputmask/jquery.inputmask.min.js"></script>
        <script src="./assets/plugins/inputmask/inputmask/inputmask.phone.extensions.min.js"></script>
        <script src="./assets/plugins/inputmask/inputmask/inputmask.extensions.min.js"></script>
        <script src="./assets/plugins/awesomplete-gh-pages/awesomplete.min.js" async></script>
        <script src="./assets/js/cabecera.js" async> </script>
        <script src="./assets/js/chat.js" async> </script>
        <script src="./assets/js/posts.js" async> </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDzFQ5ptGGip1g0Ai7Zz5Q-08JRcEJUzmk &libraries=places&callback=initAutocomplete"
        async defer></script>
        <script>
            
            $("[data-mask]").inputmask();
        </script>
    </body>
</html>
