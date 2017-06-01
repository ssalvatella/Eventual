<%-- 
    Document   : completar_perfil_social
    Created on : 01-jun-2017, 18:26:40
    Author     : Samuel
--%>

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
                    <small>Hemos visto que tu perfil no esta completo. ¡Añade más información sobre tí!</small>
                  </h1>
                  <ol class="breadcrumb">
                    <li><a href=""><i class="active fa fa-home"></i> Inicio</a></li>
                  </ol>
                </section>
                <!-- FIN Cabeza de página -->
                
                <section class="content">
                    <div class="row">
                        <div class="col-md-offset-2 col-md-5">
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                  <h3 class="box-title">Perfil</h3>
                                </div>
                                <!-- /.box-header -->
                                <!-- form start -->
                                <form method="POST" action="./CompletarPerfilSocial" role="form">
                                  <div class="box-body">
                                    <div id="form_nacimiento" class="form-group has-feedback">
                                        <label>Fecha de nacimiento</label>
                                      <input  value="<% out.print(perfil.getNacimiento()); %>" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask id="input_nacimiento" name="nacimiento" type="text" class="form-control" placeholder="¿Cúando naciste?">
                                      <span  class="glyphicon glyphicon-calendar form-control-feedback"></span>
                                    </div>
                                    <div class="form-group has-feedback">
                                      <label>Ciudad</label>
                                      <input value="<% out.print(perfil.getCiudad()); %>" class="form-control" name="ciudad" id="input_ciudad" placeholder="¿De dónde eres?" type="text">
                                      <span  class="glyphicon glyphicon-map-marker form-control-feedback"></span>
                                    </div>
                                    <div class="form-group has-feedback">
                                      <label>Estudios</label>
                                      <input value="<% out.print(perfil.getEstudios()); %>" class="form-control" name="estudios" id="input_estudios" placeholder="¿Qué has estudiado?" type="text">
                                      <span  class="fa fa-book form-control-feedback"></span>
                                    </div>
                                    <div class="form-group has-feedback">
                                      <label>Profesión</label>
                                      <input value="<% out.print(perfil.getProfesion()); %>" class="form-control" name="profesion" id="input_profesion" placeholder="¿En qué trabajas?" type="text">
                                      <span  class="fa fa-wrench form-control-feedback"></span>
                                    </div>
                                    <div class="form-group has-feedback">
                                      <label>Descripción</label>
                                      <textarea style="resize: none" class="form-control" name="descripcion" id="input_profesion" placeholder="Habla sobre tí" type="text"><% out.print(perfil.getDescripcion()); %></textarea>
                                      <span  class="fa fa-sticky-note form-control-feedback"></span>
                                    </div>
                                  </div>
                                  <!-- /.box-body -->

                                  <div class="box-footer">
                                    <a href="./Social" class="btn btn-default pull-left">Ir a inicio</a>
                                    <button type="submit" class="btn btn-primary pull-right">Guardar</button>
                                  </div>
                                </form>
                          </div>                            
                        </div>
                        <div class="col-md-3">
                            <div class="small-box bg-green">
                                <div class="inner">
                                  <h3><% out.print((int) (perfil.devuelveCompletitud() * 100)); %><sup style="font-size: 20px">%</sup></h3>

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
        <script src="./assets/plugins/inputmask/inputmask/inputmask.date.extensions.min.js"></script>
        <script src="./assets/plugins/inputmask/inputmask/inputmask.extensions.min.js"></script>
        <script src="./assets/plugins/awesomplete-gh-pages/awesomplete.min.js" async></script>
        <script src="./assets/js/cabecera.js" async> </script>
        <script src="./assets/js/chat.js" async> </script>
        <script src="./assets/js/posts.js" async> </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDzFQ5ptGGip1g0Ai7Zz5Q-08JRcEJUzmk &libraries=places&callback=initAutocomplete"
        async defer></script>
        <script>
            
            $("#input_nacimiento").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
            
            var buscadorDireccion, autocomplete;
            
            function initAutocomplete() {
                autocomplete = new google.maps.places.Autocomplete(
                    /** @type {!HTMLInputElement} */(document.getElementById('input_ciudad')),
                        {types: ['(cities)']});
                autocomplete.addListener('place_changed', completarDireccion);
            }
            
            function completarDireccion() {
                // Get the place details from the autocomplete object.
                var place = autocomplete.getPlace();
                $('#input_ciudad').val(place.address_components[1].long_name);
                $('#form_direccion').addClass('has-success');
             }

            function geolocaliza() {
              if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                  var geolocation = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                  };
                  var circle = new google.maps.Circle({
                    center: geolocation,
                    radius: position.coords.accuracy
                  });
                  autocomplete.setBounds(circle.getBounds());
                });
              }
            }
            
            geolocaliza();
        </script>
    </body>
</html>
