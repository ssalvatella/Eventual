<%-- 
    Document   : inicio
    Created on : 25-abr-2017, 16:35:08
    Author     : Samuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.eventual.herramientas.Plantilla"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | Inicio</title>
        <% out.print(Plantilla.cargarHojasCSS()); %>
    </head>
    <%  
        boolean validado = false;
        if ( request.getAttribute("validacion") != null) {
             validado = ((Boolean) request.getAttribute("validacion")).booleanValue();
        } else {
            validado = true;
        }
    %>
    <body class="hold-transition login-page">
        <div class="login-box">
          <div class="login-logo animated <% out.print(validado ? "bounce" : ""); %>">
            <a href=""><b>Even</b>Tual</a>
          </div>
          <!-- /.login-logo -->
          <div class="login-box-body animated <% out.print(validado ? "bounceInLeft" : "shake"); %>">
            <p class="login-box-msg">Identifíquese para iniciar sesión</p>

            <form action="./Inicio" method="post">
              <div id="form_email" class="form-group has-feedback  <% out.print(validado ? "" : "has-error"); %>">
                <% out.print(validado ? "" : 
                    "<label id=\"mensaje_error\" class=\"control-label\" for=\"inputError\"><i class=\"fa fa-times-circle-o\"></i> Email o contraseña incorrectos</label>"); 
                %>
                <input name="usuario" type="email" class="form-control" placeholder="Email" required>
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
              </div>
              <div id="form_contraseña" class="form-group has-feedback  <% out.print(validado ? "" : "has-error"); %>">
                <input name="contrasenia" type="password" class="form-control" placeholder="Contraseña" required>
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
              </div>
              <div class="row">
                <div class="col-xs-8">
                  <div class="checkbox icheck">
                    <label>
                      <input type="checkbox"> Recuerdame
                    </label>
                  </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                  <button id="boton_iniciar" type="submit" class="btn btn-primary btn-block btn-flat">Iniciar</button>
                </div>
                <!-- /.col -->
              </div>
            </form>

            <a href="">He olvidado mi contraseña</a><br>
            <a href="./Registrarse" class="text-center">Registrarse</a>

          </div>
          <!-- /.login-box-body -->
        </div>
        <!-- /.login-box -->

        <% out.print(Plantilla.cargarJavaScripts()); %>
        <script>
        $(function () {
          $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
          });
          
          $("input").keypress(function(event) {
            if (event.which === 13) {
                event.preventDefault();
                $("form").submit();
            }
          <%
           
            if (!validado) {
                out.print(" else { \n"
                        + "\t\t\t $('#form_email').removeClass(\"has-error\"); \n"
                        + "\t\t\t $('#form_contraseña').removeClass(\"has-error\"); \n"
                        + "\t\t\t $('#mensaje_error').remove(); \n"
                + "\t\t }");
            }
           %>
          });
           $('form').submit(function(ev) {
                ev.preventDefault(); 
                $('#boton_iniciar').html('<div class="overlay"><i class="fa fa-refresh fa-spin\"></i></div>');
                this.submit();
           });
        });
        </script>
    </body>
</html>
