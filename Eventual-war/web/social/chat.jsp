<%-- 
    Document   : chat
    Created on : 16-may-2017, 15:24:24
    Author     : Samuel
--%>

<!-- CHAT -->
<div class="box box-danger direct-chat direct-chat-danger">
    <div id="cargando" class="overlay"><i class="fa fa-refresh fa-spin"></i></div>
    <div class="box-header with-border">
      <h3 class="box-title">Chat</h3>
      <div class="box-tools pull-right">
        <span data-toggle="tooltip" title="" class="badge bg-red" data-original-title="3 nuevos mensajes">3</span>
        <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
        <button class="btn btn-box-tool" data-toggle="tooltip" title="" data-widget="chat-pane-toggle" data-original-title="Contactos"><i class="fa fa-comments"></i></button>
        <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
      </div>
    </div><!-- /.box-header -->
    <div id="cuerpo_mensajes" class="box-body">
      <!-- Conversations are loaded here -->
      <div class="direct-chat-messages">
        <!-- Message. Default to the left -->
        <div class="direct-chat-msg">
          <div class="direct-chat-info clearfix">
            <span class="direct-chat-name pull-left">Alexander Pierce</span>
            <span class="direct-chat-timestamp pull-right">23 Jan 2:00 pm</span>
          </div><!-- /.direct-chat-info -->
          <img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><!-- /.direct-chat-img -->
          <div class="direct-chat-text">
            Prueba de mensaje.
          </div><!-- /.direct-chat-text -->
        </div><!-- /.direct-chat-msg -->

        <!-- Message to the right -->
        <div class="direct-chat-msg right">
          <div class="direct-chat-info clearfix">
            <span class="direct-chat-name pull-right"><% out.print(perfil.getNombre()); %></span>
            <span class="direct-chat-timestamp pull-left">23 Jan 2:05 pm</span>
          </div><!-- /.direct-chat-info -->
          <img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><!-- /.direct-chat-img -->
          <div class="direct-chat-text">
            Esto es una respuesta de prueba.
          </div><!-- /.direct-chat-text -->
        </div><!-- /.direct-chat-msg -->
      </div><!--/.direct-chat-messages-->

      <!-- Contacts are loaded here -->
      <div class="direct-chat-contacts">
        <ul id="lista_amigos" class="contacts-list">
          <li>
            <a href="#">
              <img class="contacts-list-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="Contact Avatar">
              <div class="contacts-list-info">
                <span class="contacts-list-name">
                  Count Dracula
                  <small class="contacts-list-date pull-right">2/28/2015</small>
                </span>
                <span class="contacts-list-msg">How have you been? I was...</span>
              </div><!-- /.contacts-list-info -->
            </a>
          </li><!-- End Contact Item -->
        </ul><!-- /.contatcts-list -->
      </div><!-- /.direct-chat-pane -->
    </div><!-- /.box-body -->
    <div class="box-footer">
      <form action="#" method="post">
        <div class="input-group">
          <input name="message" placeholder="Escribe un mensaje..." class="form-control" type="text">
          <span class="input-group-btn">
            <button type="button" class="btn btn-danger btn-flat">Enviar</button>
          </span>
        </div>
      </form>
    </div><!-- /.box-footer-->
  </div>
<!-- FIN CHAT -->