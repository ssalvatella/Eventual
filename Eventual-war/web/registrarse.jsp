<%-- 
    Document   : registrarse
    Created on : 28-abr-2017, 10:28:27
    Author     : Samuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.eventual.herramientas.Plantilla"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | Registrarse </title>
        <% out.print(Plantilla.cargarHojasCSS()); %>
    </head>
    <%  
        boolean hayError = false;
        boolean errorNombre = false;
        boolean errorEmail = false;
        boolean errorContraseña = false;
        boolean errorContraseña2 = false;
        boolean errorExisteEmail = false;
        if (request.getAttribute("hayError") != null) {
             hayError = ((Boolean) request.getAttribute("hayError")).booleanValue();
             errorNombre = (request.getAttribute("errorNombre") != null) ? ((Boolean) request.getAttribute("errorNombre")).booleanValue() : false;
             errorEmail = (request.getAttribute("errorEmail") != null) ? ((Boolean) request.getAttribute("errorEmail")).booleanValue() : false;
             errorContraseña = (request.getAttribute("errorContrasenia") != null) ? ((Boolean) request.getAttribute("errorContrasenia")).booleanValue() : false;
             errorContraseña2 = (request.getAttribute("errorContrasenia2") != null) ? ((Boolean) request.getAttribute("errorContrasenia2")).booleanValue() : false;
             errorExisteEmail = (request.getAttribute("errorExisteEmail") != null) ? ((Boolean) request.getAttribute("errorExisteEmail")).booleanValue() : false;
        } 
    %>
    <body class="hold-transition login-page">
        <div class="login-box">
          <div class="login-logo animated bounce">
            <a href="">Registro</a>
          </div>
          <!-- /.login-logo -->
          <div id="bloque_formulario" class="login-box-body animated <% out.print(hayError ? "snake" : "bounceInRight"); %> ">
            <p class="login-box-msg">Introduzca sus datos para registrarse</p>
            <!-- MENSAJE DE ERROR -->
            <div class="callout callout-danger" id="error" style="<% out.print(hayError ? "" : "display: none;"); %>">
                <h4>¡Hay errores en el formulario!</h4>
                <ul>
                    <li style="<% out.print(errorNombre ? "" : "display: none;"); %>" id="error_nombre" >Introduce un nombre válido.</li>
                    <li style="<% out.print(errorEmail ? "" : "display: none;"); %>" id="error_email">El email tiene que ser válido.</li>
                    <li style="<% out.print(errorExisteEmail ? "" : "display: none;"); %>" id="error_existe_email">Este email ya existe.</li>
                    <li style="<% out.print(errorContraseña ? "" : "display: none;"); %>" id="error_contraseña">Escoge una contraseña segura.</li>
                    <li style="<% out.print(errorContraseña2 ? "" : "display: none;"); %>" id="error_contraseña2">Confirma tu contraseña escribiendola dos veces.</li>
                </ul>
            </div>
            <form action="./Registrarse" method="post">
              <!-- NOMBRE -->
              <div id="form_nombre" class="form-group has-feedback">
                <!-- <label id="error_nombre" style="display:none;" class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> Este nombre no es válido</label> -->
                <input id="input_nombre" name="nombre" type="text" class="form-control" placeholder="Nombre completo" required>
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
              </div>
              <!-- EMAIL -->
              <div id="form_email" class="form-group has-feedback">
                <!-- <label id="error_email" style="display:none;" class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> La dirección de correo debe de ser válida</label> -->
                <input id="input_email" name="email" type="email" class="form-control" placeholder="Dirección email" required>
                <span  class="glyphicon glyphicon-envelope form-control-feedback"></span>
              </div>
              <!-- CONTRASEÑA -->
              <div id="form_contraseña" class="form-group has-feedback">
                <input id="input_contraseña" name="contrasenia" type="password" class="form-control" placeholder="Contraseña" required>
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                <span style="display: none;" id="fuerza_contraseña" class="help-block">Help block with success</span>
              </div>

              <!-- REPETIR CONTRASEÑA -->
              <div id="form_contraseña2" class="form-group has-feedback">
                <input id="input_contraseña2" name="contrasenia_2" type="password" class="form-control" placeholder="Confirme la contraseña" required>
                <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
              </div>
              <div class="row">
                <div class="col-xs-8">
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                  <button id="boton_registrar" type="submit" class="btn btn-primary btn-block btn-flat">Registrarse</button>
                </div>
                <!-- /.col -->
              </div>
            </form>
            <a href="./" class="text-center">Volver al Inicio</a>

          </div>
          <!-- /.login-box-body -->
        </div>
        <!-- /.login-box -->

        <% out.print(Plantilla.cargarJavaScripts()); %>
        <script type="text/javascript" src="./assets/plugins/bootstrap-strength-meter/password-score.js"></script>
        <script type="text/javascript" src="./assets/plugins/bootstrap-strength-meter/password-score-options.js"></script>
        <script type="text/javascript" src="./assets/plugins/bootstrap-strength-meter/bootstrap-strength-meter.js"></script>
        <script>
        $(function () {
           
            function nombreValido(texto) {
                var regex = /^[a-zA-Z ]{2,30}$/;
                if (regex.test(texto)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            
            function emailValido(email) {
                var re = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
                return re.test(email);
            }
           
            function compruebaContrasenia() {
                if ($('#input_contraseña').val() != "") {
                    if ($('#input_contraseña').val() != $('#input_contraseña2').val()) {
                        $('#form_contraseña2').removeClass('has-success');
                        $('#form_contraseña2').addClass('has-error');
                    } else {
                        $('#form_contraseña2').removeClass('has-error');
                        $('#form_contraseña2').addClass('has-success');
                        $('#error_contraseña2').hide();
                    }
                }
                var nivel = $('#fuerza_contraseña').html();
                if (nivel === "Débil") {
                    $('#form_contraseña').removeClass('has-success');
                    $('#form_contraseña').removeClass('has-error');
                    $('#form_contraseña').addClass('has-warning');
                    $('#error_contraseña').hide();
                } else if (nivel === "Muy débil" || nivel === "Ridícula") {
                    $('#form_contraseña').removeClass('has-success');
                    $('#form_contraseña').removeClass('has-warning');
                    $('#form_contraseña').addClass('has-error');   
                } else {
                    $('#form_contraseña').removeClass('has-error');
                    $('#form_contraseña').removeClass('has-warning');
                    $('#form_contraseña').addClass('has-success');
                    $('#error_contraseña').hide();
                }
            }
           
          $('#input_contraseña').strengthMeter('text', {
            container: $('#fuerza_contraseña'),
            hierarchy: {
                '0': ['help-block text-red', 'Ridícula'],
                '10': ['help-block text-red', 'Muy débil'],
                '20': ['help-block text-yellow', 'Débil'],
                '30': ['help-block text-green', 'Buena'],
                '40': ['help-block text-green', 'Fuerte'],
                '50': ['help-block text-green', 'Muy fuerte']
            }
          });

  
          $('#input_nombre').keypress(function(event) {
              if (nombreValido($('#input_nombre').val())) {
                  $('#form_nombre').removeClass('has-error');
                  $('#form_nombre').addClass('has-success');
                  if ($('#error_nombre').is(":visible")) {
                      $('#error_nombre').hide();
                  }
              } else {
                  $('#form_nombre').removeClass('has-success');
                  $('#form_nombre').addClass('has-error');
                  if (!$('#error_nombre').is(":visible")) {
                      $('#error_nombre').show();
                  }
              }
          });
          
          $('#input_email').keypress(function(event) {
              if (emailValido($('#input_email').val())) {
                  $('#form_email').removeClass('has-error');
                  $('#form_email').addClass('has-success');
                  if ($('#error_email').is(":visible")) {
                      $('#error_email').hide();
                  }
              } else {
                  $('#form_email').removeClass('has-success');
                  $('#form_email').addClass('has-error');
                  if (!$('#error_email').is(":visible")) {
                      $('#error_email').show();
                  }
              }
          });
          
          $('#input_contraseña').keypress(function(evento) {
              $('#fuerza_contraseña').show();
          });
          
          $('#input_contraseña, #input_contraseña2').keyup(compruebaContrasenia);
          
        $('#boton_registrar').click(function(ev) {
            ev.preventDefault(); 
            $('#boton_registrar').html('<div class="overlay"><i class="fa fa-refresh fa-spin\"></i></div>');

            if (comprobarFormulario()) {
               $('form').submit();
            } else {
               $('#bloque_formulario').removeClass('animated');
               $('#bloque_formulario').removeClass('bounceInRight');
               $('#bloque_formulario').removeClass('snake');
               $('#bloque_formulario').addClass('animated snake');
               $('#boton_registrar').html('Registrarse');
            }
        });
           
        function mostrarError(elemento) {
            $('#error').show();
            $(elemento).show();
        }
        
        function comprobarFormulario() {
            
            var formularioCorrecto = true;
            
            if (!nombreValido($('#input_nombre').val())) {
                mostrarError('#error_nombre');
                formularioCorrecto = false;
            }
            
            if (!emailValido($('#input_email').val())) {
                mostrarError('#error_email');
                formularioCorrecto = false;
            }
            
            var nivel = $('#fuerza_contraseña').html();
            if (nivel === "Muy débil" || nivel === "Ridícula" || $('#input_contraseña').val() === "") {
                mostrarError('#error_contraseña');
                formularioCorrecto = false;
            }
            
             if ($('#input_contraseña').val() !== $('#input_contraseña2').val()) {
                mostrarError('#error_contraseña2');
                formularioCorrecto = false;
             }
             
            return formularioCorrecto;
        }
        });
        </script>
    </body>
</html>
