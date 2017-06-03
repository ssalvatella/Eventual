<%-- 
    Document   : cabecera
    Created on : 10-may-2017, 8:51:45
    Author     : Samuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<span style="display: none" id="ID_USUARIO"><% out.write(perfil.getId() + ""); %></span>

  <header class="main-header">

    <!-- Logo -->
    <a href="./Social" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>E</b>TL</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Even</b>Tual</span>
    </a>
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
            <!-- BUSCADOR -->
            <form id="ir_perfil" method="get" action="./PerfilUsuario" class="navbar-form navbar-left" role="search">
                <div class="form-group">
                  <input name="perfil" id="buscador" type="text" class="form-control"  id="navbar-search-input" placeholder="Buscar..."> 
                </div>
            </form>
            <!-- FIN BUSCADOR -->
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="./assets/plugins/admin-lte/img/avatar5.png" class="user-image" alt="<% out.print(perfil.getNombre()); %>">
              <span id="NOMBRE" class="hidden-xs"><% out.print(perfil.getNombre()); %></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="./assets/plugins/admin-lte/img/avatar5.png" class="img-circle" alt="<% out.print(perfil.getNombre()); %>">

                <p>
                  <% out.print(perfil.getNombre()); %> - <% out.print((perfil.getProfesion() != null) ? perfil.getProfesion() : ""); %>
                  <small>Miembro desde el <% out.print(fechaRegistro); %></small>
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Eventos</a>
                  </div>
                  <div class="col-xs-4 text-center">
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Amigos</a>
                  </div>
                </div>
                <!-- /.row -->
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="./PerfilUsuario?perfil=<% out.print(usuario.getId()); %>" class="btn btn-default btn-flat">Perfil</a>
                </div>
                <div class="pull-right">
                  <span id="salir" onclick="salir()" class="btn btn-default btn-flat">Salir</span>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li>
        </ul>
      </div>

    </nav>
  </header>
                
          