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
        boolean error = false;
        if ( request.getAttribute("error") != null) {
             error = ((Boolean) request.getAttribute("error")).booleanValue();
        } else {
            error = true;
        }
        String mensaje = (error) ? "Ha ocurrido un error inesperado. Por favor, <a href=\"./Registrarse\">vuelve</a> a intentarlo más tarde." : 
                "Bienvenido a <strong>Eventual</strong>. Ya puedes hacer uso de la página identificándote desde el <a href=\"./Inicio\">Inicio</a>";
        String tituloMensaje = (error) ? "Ups!" : "Registro completado!";
    %>
    <body class="hold-transition login-page">
        <div class="login-box">
          <div class="login-logo animated">
            <a href=""><b>Even</b>Tual</a>
          </div>
          <!-- /.login-logo -->
          <div class="login-box-body animated">
            <div class="alert <% out.print((error) ? "alert-error" : "alert-success"); %> alert-dismissible">
              <h4><i class="icon fa fa-check"></i><% out.print(tituloMensaje); %></h4>
              <% out.print(mensaje); %>
            </div>
          </div>
          <!-- /.login-box-body -->
        </div>
        <!-- /.login-box -->

        <% out.print(Plantilla.cargarJavaScripts()); %>
    </body>
</html>
