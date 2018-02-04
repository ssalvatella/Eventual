<%-- 
    Document   : screencast
    Created on : 12-jun-2017, 1:34:32
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
        <link href="Screen Cast Eventual_embed.css" rel="stylesheet" type="text/css">
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
        <div class="">
          <div class="login-logo animated bounce">
            <a href="./"><b>Even</b>Tual</a>
            <h1>Vídeo sobre el proyecto</h1>
          </div>
          <!-- /.login-box-body -->
        </div>
        <div style="padding-top:-200px" class="animated bounceInUp container">
            <video controls>
            <source src="./screencast/Screen_Cast_Eventual.webm" type="video/webm">
           
            Your browser does not support HTML5 video.
            </video>
        </div>
        <!-- /.login-box -->

        <% out.print(Plantilla.cargarJavaScripts()); %>
    </body>
</html>
