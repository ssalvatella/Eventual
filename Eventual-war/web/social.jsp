<%-- 
    Document   : social
    Created on : 09-may-2017, 16:40:28
    Author     : Samuel
--%>

<%@page import="com.eventual.stateless.modelo.PerfilSocial"%>
<%@page import="com.eventual.herramientas.Plantilla"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eventual | Inicio</title>
        <% out.print(Plantilla.cargarHojasCSS()); %>
    </head>
    <%
        PerfilSocial perfil = (PerfilSocial) request.getAttribute("perfil");
    %>
    <body>
        <h1>Hello <% out.print(perfil.getNombre()); %></h1>
        <% out.print(Plantilla.cargarJavaScripts()); %>
    </body>
</html>