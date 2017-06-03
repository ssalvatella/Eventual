<%-- 
    Document   : menu
    Created on : 10-may-2017, 16:25:13
    Author     : Samuel
--%>
<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="./assets/img/bar1.jpg" class="img-circle" alt="<% out.print(perfil.getNombre()); %>">
        </div>
        <div class="pull-left info">
          <p><% out.print(perfil.getNombre()); %></p>
          <a href="#"><i class="fa fa-circle text-success"></i> Conectado</a>
        </div>
      </div>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MENU</li>
         <li>
          <a href="">
            <i class="fa fa-home"></i> <span>Inicio</span>
          </a>
        </li>
        <li>
          <a href="">
            <i class="fa fa-users"></i> <span>Amigos</span>
          </a>
        </li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>