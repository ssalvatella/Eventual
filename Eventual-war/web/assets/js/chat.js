

var ws = null;

var destinatarioActual;
var nombreDestinatarioActual;
var audio_notificacion = new Audio('../assets/sonidos/mensaje_notificacion.mp3');

var mensaje_presentacion = '{ mensaje: {tipo: "CONEXION", idUsuario: ' + $('#ID_USUARIO').text() + ', nombre: "' + $('#NOMBRE').text() + '"} }';

$('#boton_enviar_mensaje').click(enviarMensaje);

$('#input_mensaje').keypress(function(event){

  if(event.keyCode == 13){
    event.preventDefault();
    $('#boton_enviar_mensaje').click();
  }
});

function conectar() {
    
    var URL = "ws://localhost:8080/Eventual-war/chat";
    
    if ('WebSocket' in window) {
        ws = new WebSocket(URL);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(URL);
    } else {
        alert('Tu navegador no soporta WebSockets');
        return;
    }
    
    ws.onopen = function () {
        console.log("Conexión realizada con éxito.");
        enviar(mensaje_presentacion);
        $('#cargando').hide();
    };
    
    ws.onmessage = function (evento) {
        var mensaje = evento.data;
        console.log("Mensaje recibido: ");
        console.log(mensaje);
        procesarMensaje(JSON.parse(mensaje));
    };
    
    ws.onclose = function () {
        console.log("Desconexión realizada!");
    };
    
    ws.onerror = function (event) {
        console.log("ERROR!");
        console.log(event);
    };

}

function enviar(mensaje) {
    if (ws !== null) {
        console.log("Enviando mensaje : " + mensaje);
        ws.send(mensaje);
    }
}

function procesarMensaje(recibido) {
    switch(recibido.tipo) {
        case "CONECTADOS":
            console.log(recibido.conectados.length + " amigos conectados.");
            mostrarAmigos(recibido.conectados);
            break;
        case "CONECTADO":
            console.log("Amigo "+ recibido.nombre +" conectado.");
            conectarAmigo(recibido);
            break;
        case "DESCONECTADO":
            console.log("Desconectado amigo " + recibido.id);
            desconectarAmigo(recibido.id);
            break;
        case "MENSAJE":
            recibirMensaje(recibido);
            break;
    }    
}

function mostrarAmigos(amigos) {
    
    $('#lista_amigos').empty();
    for (var i = 0; i < amigos.length; i++) {
        $('#lista_amigos').append('<li name="'+ amigos[i].nombre +'" style="cursor:pointer;" value="' + amigos[i].id +'"><img class="contacts-list-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="Avatar de contacto"><div class="contacts-list-info"><span class="contacts-list-name">' + amigos[i].nombre + '</span></div></li>');
        $("li[value='" + amigos[i].id + "']").click(function() {
            abrirChat(this.value, this.getAttribute("name"));
        });
    }
    
}

function conectarAmigo(amigo) {
    $('#lista_amigos').append('<li name="'+ amigo.nombre +'" style="cursor:pointer;" value="' + amigo.id +'"><img class="contacts-list-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="Avatar de contacto"><div class="contacts-list-info"><span class="contacts-list-name">' + amigo.nombre + '</span></div></li>');
    $("li[value='" + amigo.id + "']").click(function() {
        abrirChat(this.value, amigo.nombre);
    });
    new Noty({
        timeout: 2000,
        layout: 'bottomRight',
        theme: 'metroui',
        type: 'information',
        progressBar: true,
        text: amigo.nombre + ' en línea.'
    }).show();
}

function desconectarAmigo(id) {
    $("li[value='" + id + "']").remove();
}

function abrirChat(id, nombre) {
    $('#cuerpo_mensajes').empty();
    $('#boton_contactos').click();
    $('#contador_mensajes').hide();  
    destinatarioActual = id;
    nombreDestinatarioActual = nombre;
    $('#cargando').show();
    $.ajax({
        type: "POST",
        url: "./Mensajes",
        data: {
            'peticion' : 'mensajes',
            'id_usuario' : $('#ID_USUARIO').text(),
            'id_amigo' : id
        },
        success: function(respuesta) {
            console.log(respuesta);
             mostrarMensajes(respuesta, nombre);
            $('#cargando').hide();
        }
    });
}

function mostrarMensajes(mensajes, nombre) {
    for (var i = 0; i < mensajes.length; i++) {
        // Ese mensaje lo he envíado yo!
        if (mensajes[i].emisor == $('#ID_USUARIO').text()) {
            $('#cuerpo_mensajes').append('<div class="direct-chat-msg right"><div class="direct-chat-info clearfix"><span class="direct-chat-name pull-right">'+ $('#NOMBRE').text() +'</span><span class="direct-chat-timestamp pull-left">'+ formateaFecha(new Date(mensajes[i].fecha)) +'</span></div><img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><div class="direct-chat-text">'+ mensajes[i].texto +'</div></div>');
        } else { // Este mensaje me lo enviaron
            $('#cuerpo_mensajes').append('<div class="direct-chat-msg"><div class="direct-chat-info clearfix"><span class="direct-chat-name pull-left">'+ nombre +'</span><span class="direct-chat-timestamp pull-right">'+ formateaFecha(new Date(mensajes[i].fecha)) +'</span></div><img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><div class="direct-chat-text">'+ mensajes[i].texto +'</div></div>');
        }
    }
}

function enviarMensaje() {
    
    if ($('#input_mensaje').val() !== "" && destinatarioActual !== null) {
        var texto = $('#input_mensaje').val();
        var emisor = $('#ID_USUARIO').text();
        var mensaje= '{ mensaje: {tipo: "MENSAJE", emisor: ' + emisor + ', destinatario: "' + destinatarioActual + '", mensaje: "' + texto + '"} }';    
        enviar(mensaje);
        añadirMensajePropio(texto);
        $('#input_mensaje').val("");
    }
}

function recibirMensaje(mensaje) {
    
    // Si el chat de ese usuario esta abierto
    if (destinatarioActual === mensaje.emisor) {
        
         $('#cuerpo_mensajes').append('<div class="direct-chat-msg"><div class="direct-chat-info clearfix"><span class="direct-chat-name pull-left">'+ nombreDestinatarioActual +'</span><span class="direct-chat-timestamp pull-right">'+ formateaFecha(new Date()) +'</span></div><img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><div class="direct-chat-text">'+ mensaje.texto +'</div></div>');
        
    } else { // Si no esta abierto el chat
        var mensajes_no_leidos = $('#contador_mensajes').text();
        console.log(mensajes_no_leidos);
        if (mensajes_no_leidos = 0) {
          $('#contador_mensajes').show();
        } 
        mensajes_no_leidos++;
        $('#contador_mensajes').text(mensajes_no_leidos);
        var nombreDelEmisor = $('li[value="'+ mensaje.emisor +'"]').attr("name");
        new Noty({
            timeout: 2000,
            layout: 'bottomRight',
            theme: 'metroui',
            type: 'success',
            progressBar: true,
            text: nombreDelEmisor + ' dice: ' + mensaje.texto
        }).show();
        audio_notificacion.play();
    }
    
}

function añadirMensajePropio(mensaje) {
    $('#cuerpo_mensajes').append('<div class="direct-chat-msg right"><div class="direct-chat-info clearfix"><span class="direct-chat-name pull-right">'+ $('#NOMBRE').text() +'</span><span class="direct-chat-timestamp pull-left">'+ formateaFecha(new Date()) +'</span></div><img class="direct-chat-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="message user image"><div class="direct-chat-text">'+ mensaje +'</div></div>');
}

function formateaFecha(fecha) {
    var monthNames = [
    "Enero", "Febrero", "Marzo",
    "Abril", "Mayo", "Junio", "Julio",
    "Agosto", "Septiembre", "Octubre",
    "Noviembre", "Diciembre"
  ];

  var day = fecha.getDate();
  var monthIndex = fecha.getMonth();
  var year = fecha.getFullYear();
  
  var hora = fecha.getHours();
  var minutos = fecha.getMinutes();

  return day + ' ' + monthNames[monthIndex] + ' ' + hora + ':' + minutos;
}
    
conectar();
